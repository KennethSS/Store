package com.github.kennethss

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.github.kennethss.StoreHelper.getValueForLifeCycleOwner
import com.github.kennethss.StoreHelper.saveCastedPreferenceKeyAndValue
import com.github.kennethss.StoreHelper.valueForType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Store.getPreferenceName())

// Write
suspend fun Context.writeToStore(key: String, value: Any) {
  dataStore.edit { preferences ->
    saveCastedPreferenceKeyAndValue(preferences, key, value)
  }
}

fun LifecycleOwner.setValueForLifeCycleOwner(context: Context, key: String, value: Any) =
  lifecycleScope.launch {
    context.writeToStore(key, value)
  }

fun AppCompatActivity.setStore(key: String, value: Any) {
    setValueForLifeCycleOwner(this, key, value)
}

fun Fragment.setStore(key: String, value: Any) {
    viewLifecycleOwner.setValueForLifeCycleOwner(requireContext(), key, value)
}

fun ViewModel.setStore(key: String, value: Any) = viewModelScope.launch {
  Store.getApplicationContext().writeToStore(key, value)
}

inline fun <reified T> AppCompatActivity.getStore(
  key: String,
  crossinline action: (value: T?) -> Unit
) {
  getValueForLifeCycleOwner(this, key, action)
}

inline fun <reified T> Fragment.getStore(
    key: String,
    crossinline action: (value: T?) -> Unit
) {
    viewLifecycleOwner.getValueForLifeCycleOwner(requireContext(), key, action)
}

inline fun <reified T : Any> ViewModel.getStore(
    key: String,
    crossinline action: (value: T?) -> Unit
) = viewModelScope.launch {
    valueForType<T>(Store.getApplicationContext(), key).collectLatest { value ->
      if (value != null) {
        action(value as T)
      } else {
        action(value)
      }
    }
}

