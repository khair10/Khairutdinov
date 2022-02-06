package com.khair.tinkofflabapp.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khair.tinkofflabapp.presentation.categories.Category
import com.khair.tinkofflabapp.presentation.categories.CategoryFragment
import com.khair.tinkofflabapp.presentation.random.RandomFragment

class PagesAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CategoryFragment.newInstance(category = Category.Latest)
            1 -> CategoryFragment.newInstance(category = Category.Top)
            2 -> CategoryFragment.newInstance(category = Category.Hot)
            3 -> RandomFragment.newInstance()
            else -> throw IllegalStateException()
        }
    }
}