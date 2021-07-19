package com.kennethss.store.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kennethss.store.getStore
import com.kennethss.store.sample.databinding.ActivityMainBinding
import com.kennethss.store.setStore

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
  }
}