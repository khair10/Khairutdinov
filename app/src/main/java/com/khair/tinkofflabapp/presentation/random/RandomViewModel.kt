package com.khair.tinkofflabapp.presentation.random

import androidx.lifecycle.*
import com.khair.tinkofflabapp.data.repository.GifPostRepository
import com.khair.tinkofflabapp.presentation.SingleLiveAction
import com.khair.tinkofflabapp.presentation.categories.Action
import com.khair.tinkofflabapp.presentation.categories.Event
import com.khair.tinkofflabapp.presentation.categories.State
import com.khair.tinkofflabapp.presentation.model.GifCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(private val repository: GifPostRepository) : ViewModel() {

    private val _list: MutableList<GifCard> = ArrayList()
    private var currentItem = 0
    private var itemsCount = 0

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    val backVisibility: LiveData<Boolean> = Transformations.map(_state) {
        currentItem == 0
    }

    val action: SingleLiveAction<Action> = SingleLiveAction()

    fun obtainEvent(event: Event) {
        when (event) {
            is Event.OnScreenShown -> {
                next()
            }
            is Event.OnNext -> {
                next()
            }
            is Event.OnBack -> {
                back()
            }
        }
    }

    private fun next() {
        if(currentItem < itemsCount - 1) {
            _state.postValue(State.ShowItem(++currentItem))
        } else {
            viewModelScope.launch {
                try {
                    _state.postValue(State.Loading)
                    val gifPost = repository.getGifPost()
                    _list.add(gifPost)
                    itemsCount = _list.size
                    _state.postValue(State.UpdateAndShowItem(++currentItem, _list))
                } catch (e: Exception) {
                    action.postValue(Action.ShowError(e.message ?: "Unknown error"))
                }
            }
        }
    }

    private fun back() {
        if (--currentItem == 0) {
            _state.postValue(State.ShowFirst)
        } else {
            _state.postValue(State.ShowItem(currentItem))
        }
    }
}