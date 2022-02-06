package com.khair.tinkofflabapp.presentation.categories

import com.khair.tinkofflabapp.presentation.model.GifCard

sealed class State {
    object Loading: State()
    class ShowItem(val currentPage: Int): State()
    object ShowFirst : State()
    class UpdateAndShowItem(val currentPage: Int, val data: List<GifCard>): State()
}

sealed class Event {
    object OnScreenShown : Event()
    object OnNext : Event()
    object OnBack : Event()
}

sealed class Action {
    data class ShowError(val message: String): Action()
}