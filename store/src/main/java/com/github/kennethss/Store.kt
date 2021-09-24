package com.github.kennethss

import android.content.Context

object Store {

  private var applicationContext: Context? = null
  private var preferencesName: String? = null
  private val migrationNames = arrayListOf<String>()

  fun context(context: Context): Store {
    applicationContext = context
    return this
  }

  fun preferencesName(name: String): Store {
    preferencesName = name
    return this
  }

  fun migrations(names: List<String>) {
    migrationNames.addAll(names)
  }

  fun getPreferenceName(): String = preferencesName ?: "preference_name"
  fun getApplicationContext(): Context = applicationContext ?: throw Exception("Require applicationContext use to Store")
  fun getMigrationsNames(): List<String> = migrationNames
}