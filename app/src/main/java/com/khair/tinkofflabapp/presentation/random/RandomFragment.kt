package com.khair.tinkofflabapp.presentation.random

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.khair.tinkofflabapp.databinding.RandomFragmentBinding
import com.khair.tinkofflabapp.presentation.categories.Action
import com.khair.tinkofflabapp.presentation.categories.Event
import com.khair.tinkofflabapp.presentation.categories.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomFragment : Fragment() {

    companion object {
        fun newInstance() = RandomFragment()
    }

    private val viewModel: RandomViewModel by viewModels()
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
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is State.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is State.ShowFirst -> {
                    binding.pager.currentItem = 0
                    binding.progress.visibility = View.GONE
                }
                is State.ShowItem -> {
                    binding.pager.currentItem = it.currentPage
                    binding.progress.visibility = View.GONE
                }
                is State.UpdateAndShowItem -> {
                    binding.pager.apply {
                        (adapter as? GifCardAdapter)?.addAll(it.data)
                        binding.pager.currentItem = it.currentPage
                    }
                    binding.progress.visibility = View.GONE
                }
            }
        }
        viewModel.action.observe(viewLifecycleOwner) {
            when(it){
                is Action.ShowError -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.next.setOnClickListener {
            viewModel.obtainEvent(Event.OnNext)
        }
        binding.back.setOnClickListener {
            viewModel.obtainEvent(Event.OnBack)
        }
        viewModel.backVisibility.observe(viewLifecycleOwner) {
            binding.back.visibility = if(it) View.GONE else View.VISIBLE
        }
        viewModel.obtainEvent(Event.OnScreenShown)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}