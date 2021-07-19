package com.kennethss.store.sample

import androidx.lifecycle.ViewModel
import com.kennethss.store.getStore

class MainViewModel : ViewModel() {
  init {
    getStore<String>("key") {

    }
  }
}