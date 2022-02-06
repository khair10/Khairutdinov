package com.khair.tinkofflabapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.khair.tinkofflabapp.R
import com.khair.tinkofflabapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = PagesAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.latest)
                1 -> tab.text = getString(R.string.top)
                2 -> tab.text = getString(R.string.hot)
                3 -> tab.text = getString(R.string.random)
            }
        }.attach()
    }
}