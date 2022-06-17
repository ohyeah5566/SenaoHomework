package com.ohyeah5566.senaohw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ohyeah5566.senaohw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}