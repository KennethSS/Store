package com.kennethss.store

import android.content.Context

object Store {

  private var applicationContext: Context? = null
  private var preferencesName: String? = null

  fun context(context: Context): Store {
    this.applicationContext = context
    return this
  }

  fun preferencesName(name: String): Store {
    this.preferencesName = name
    return this
  }

  fun getPreferenceName(): String = preferencesName ?: "preference_name"
  fun getApplicationContext(): Context = applicationContext ?: throw Exception("Require applicationContext use to Store")
}