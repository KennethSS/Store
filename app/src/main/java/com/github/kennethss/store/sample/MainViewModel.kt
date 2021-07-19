package com.github.kennethss.store.sample

import androidx.lifecycle.ViewModel
import com.github.kennethss.getStore

class MainViewModel : ViewModel() {
  init {
    getStore<String>("key") {

    }
  }
}