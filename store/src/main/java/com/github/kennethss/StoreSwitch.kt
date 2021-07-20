package com.github.kennethss

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.findViewTreeLifecycleOwner

class StoreSwitch @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwitchCompat(context, attrs, defStyleAttr) {

  private val key: String
  private val defaultValue: Boolean

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.StoreSwitch, 0, 0
    ).apply {
      try {
        key = getString(R.styleable.StoreSwitch_key) ?: "N/A"
        defaultValue = getBoolean(R.styleable.StoreSwitch_default_value, false)
      } finally {
        recycle()
      }
    }

    setOnCheckedChangeListener { buttonView, isChecked ->
      println("StoreSwitch: Set Value: $isChecked")
      findViewTreeLifecycleOwner()?.setValueForLifeCycleOwner(context, key, isChecked)
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
      lifecycleOwner.getValueForLifeCycleOwner<Boolean>(context, key) { value ->
        println("StoreSwitch: Value: $value")
        isChecked = value ?: defaultValue
      }
    }
  }
}