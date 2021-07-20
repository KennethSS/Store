package com.github.kennethss

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object StoreHelper {
  inline fun <reified T> valueForType(context: Context, key: String) = when (T::class) {
    Int::class -> context.loadPreference(intPreferencesKey(key))
    Long::class -> context.loadPreference(longPreferencesKey(key))
    Float::class -> context.loadPreference(floatPreferencesKey(key))
    Double::class -> context.loadPreference(doublePreferencesKey(key))
    Boolean::class -> context.loadPreference(booleanPreferencesKey(key))
    String::class -> context.loadPreference(stringPreferencesKey(key))
    else -> throw ClassCastException()
  }

  fun saveCastedPreferenceKeyAndValue(
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

  inline fun <reified T> LifecycleOwner.getValueForLifeCycleOwner(
    context: Context,
    key: String,
    crossinline action: (value: T?) -> Unit
  ) = lifecycleScope.launch {
    valueForType<T>(context, key).collectLatest { value ->
      if (value != null) {
        action(value as T)
      } else {
        action(value)
      }
      cancel()
    }
  }

  inline fun <reified T> Context.loadPreference(
    key: Preferences.Key<T>
  ): Flow<T?> {
    return dataStore.data.map { preference ->
      preference[key]
    }
  }
}