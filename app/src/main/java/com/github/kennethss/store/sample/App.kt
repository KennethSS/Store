package com.github.kennethss.store.sample

import android.app.Application
import com.github.kennethss.Store

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    Store.context(this)
      .preferencesName("preferences_name")
  }
}