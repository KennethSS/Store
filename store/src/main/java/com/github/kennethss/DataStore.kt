package com.github.kennethss

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Store.getPreferenceName())


// Write
suspend fun Context.writeToStore(key: String, value: Any) {
  dataStore.edit { preferences ->
    saveCastedPreferenceKeyAndValue(preferences, key, value)
  }
}

inline fun <reified T> LifecycleOwner.getValueForLifeCycleOwner(
  context: Context,
  key: String,
  crossinline action: (value: T) -> Unit
) = lifecycleScope.launch {
  valueForType<T>(context, key).collectLatest {
    action(it as T)
  }
}

private fun LifecycleOwner.setValueForLifeCycleOwner(context: Context, key: String, value: Any) =
  lifecycleScope.launch {
    context.writeToStore(key, value)
  }

fun ViewModel.setStore(key: String, value: Any) = viewModelScope.launch {
  Store.getApplicationContext().writeToStore(key, value)
}

fun AppCompatActivity.setStore(key: String, value: Any) {
    setValueForLifeCycleOwner(this, key, value)
}

fun Fragment.setStore(key: String, value: Any) {
    viewLifecycleOwner.setValueForLifeCycleOwner(requireContext(), key, value)
}

inline fun <reified T> AppCompatActivity.getStore(
  key: String,
  crossinline action: (value: T) -> Unit
) {
  getValueForLifeCycleOwner(this, key, action)
}

inline fun <reified T> Fragment.getStore(
    key: String,
    crossinline action: (value: T) -> Unit
) {
    viewLifecycleOwner.getValueForLifeCycleOwner(requireContext(), key, action)
}

inline fun <reified T : Any> ViewModel.getStore(
    key: String,
    crossinline action: (value: T) -> Unit
) = viewModelScope.launch {
    valueForType<T>(Store.getApplicationContext(), key).collectLatest {
        action(it as T)
    }
}

inline fun <reified T> valueForType(context: Context, key: String) = when (T::class) {
  Int::class -> context.loadPreference(intPreferencesKey(key))
  Long::class -> context.loadPreference(longPreferencesKey(key))
  Float::class -> context.loadPreference(floatPreferencesKey(key))
  Double::class -> context.loadPreference(doublePreferencesKey(key))
  Boolean::class -> context.loadPreference(booleanPreferencesKey(key))
  String::class -> context.loadPreference(stringPreferencesKey(key))
  else -> throw ClassCastException()
}

inline fun <reified T> Context.loadPreference(
  key: Preferences.Key<T>
): Flow<T?> {
  return dataStore.data.map { preference ->
    preference[key]
  }
}

private fun saveCastedPreferenceKeyAndValue(
  preferences: MutablePreferences,
  key: String,
  value: Any
) = when (value) {
  is Int -> preferences[intPreferencesKey(key)] = value
  is Double -> preferences[doublePreferencesKey(key)] = value
  is String -> preferences[stringPreferencesKey(key)] = value
  is Boolean -> preferences[booleanPreferencesKey(key)] = value
  is Float -> preferences[floatPreferencesKey(key)] = value
  is Long -> preferences[longPreferencesKey(key)] = value
  else -> throw ClassCastException()
}