package com.khair.tinkofflabapp.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.khair.tinkofflabapp.databinding.RandomFragmentBinding
import com.khair.tinkofflabapp.presentation.random.GifCardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    companion object {
        private const val CATEGORY = "CATEGORY"
        fun newInstance(category: Category) = CategoryFragment().apply {
            arguments = Bundle().apply { putString(CATEGORY, category.name) }
        }
    }

    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: RandomFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RandomFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = GifCardAdapter()
        binding.pager.isUserInputEnabled = false
        viewModel.category = Category.getCategory(arguments?.getString(CATEGORY, null))
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is State.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is State.ShowFirst -> {
                    binding.pager.currentItem = 0
                }
                is State.ShowItem -> {
                    binding.pager.currentItem = it.currentPage
                }
                is State.UpdateAndShowItem -> {
                    updateAndShow(it)
                }
            }
        }
        viewModel.backVisibility.observe(viewLifecycleOwner) {
            binding.back.visibility = if (it) View.GONE else View.VISIBLE
        }
        viewModel.action.observe(viewLifecycleOwner) {
            when(it){
                is Action.ShowError -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.obtainEvent(Event.OnScreenShown)
        binding.next.setOnClickListener {
            viewModel.obtainEvent(Event.OnNext)
        }
        binding.back.setOnClickListener {
            viewModel.obtainEvent(Event.OnBack)
        }
    }

    private fun updateAndShow(it: State.UpdateAndShowItem) {
        (binding.pager.adapter as? GifCardAdapter)?.addAll(it.data)
        binding.pager.currentItem = it.currentPage
        if (it.data.isEmpty()) {
            binding.content.visibility = View.GONE
            binding.message.visibility = View.VISIBLE
        } else {
            binding.content.visibility = View.VISIBLE
            binding.message.visibility = View.GONE
        }
        binding.progress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}