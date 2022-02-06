package com.khair.tinkofflabapp.presentation.categories

import androidx.lifecycle.*
import com.khair.tinkofflabapp.data.repository.GifPostRepository
import com.khair.tinkofflabapp.presentation.SingleLiveAction
import com.khair.tinkofflabapp.presentation.model.GifCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: GifPostRepository) : ViewModel() {

    var category: Category = Category.Latest
    private var page = 0
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
                screenShown()
            }
            is Event.OnNext -> {
                next()
            }
            is Event.OnBack -> {
                back()
            }
        }
    }

    private fun screenShown() {
        if (_list.isEmpty()) {
            viewModelScope.launch {
                try {
                    _state.postValue(State.Loading)
                    val list = repository.getList(category.name, page++)
                    _list.addAll(list)
                    itemsCount = _list.size
                    _state.postValue(State.UpdateAndShowItem(currentItem, _list))
                }catch (e: Exception){
                    action.postValue(Action.ShowError(e.message ?: "Unknown error"))
                }
            }
        } else {
            _state.postValue(State.UpdateAndShowItem(currentItem, _list))
        }
    }

    private fun next() {
        if (currentItem == itemsCount - 1) {
            viewModelScope.launch {
                try {
                    _state.postValue(State.Loading)
                    val list = repository.getList(category.name, page++)
                    _list.addAll(list)
                    itemsCount = _list.size
                    _state.postValue(State.UpdateAndShowItem(++currentItem, _list))
                } catch (e: Exception) {
                    action.postValue(Action.ShowError(e.message ?: "Unknown error"))
                }
            }
        } else {
            _state.postValue(State.ShowItem(++currentItem))
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