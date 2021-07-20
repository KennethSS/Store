package com.github.kennethss.store.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.github.kennethss.*
import com.github.kennethss.store.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    /*binding.write.setOnClickListener {
      setStore(binding.inputKey.text.toString(), binding.input.text.toString())
    }

    binding.read.setOnClickListener {
      getStore<String>(binding.inputKey.text.toString()) {
        binding.title.text = it
      }
    }*/

    getStore<Boolean>(getString(R.string.some_key)) {
      println("SomeValue $it")
    }
  }
}