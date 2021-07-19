package com.kennethss.store.sample

import android.app.Application
import com.kennethss.store.Store

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    Store.context(this)
      .preferencesName("preferences_name")
  }
}