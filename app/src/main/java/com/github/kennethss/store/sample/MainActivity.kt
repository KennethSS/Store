package com.github.kennethss.store.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.github.kennethss.getStore
import com.github.kennethss.store.sample.databinding.ActivityMainBinding
import com.github.kennethss.setStore
import com.github.kennethss.valueForType
import com.github.kennethss.writeToStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    binding.write.setOnClickListener {
      setStore(binding.inputKey.text.toString(), binding.input.text.toString())
    }

    binding.read.setOnClickListener {
      getStore<String>(binding.inputKey.text.toString()) {
        binding.title.text = it
      }
    }

    lifecycleScope.launch {
      valueForType<String>(this@MainActivity, "").collectLatest {

      }
    }

    lifecycleScope.launch {
      writeToStore("key", false)
    }
  }
}