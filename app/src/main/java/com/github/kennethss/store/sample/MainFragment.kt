package com.github.kennethss.store.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.kennethss.getStore

class MainFragment : Fragment() {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getStore<String>("key") {

    }
  }
}