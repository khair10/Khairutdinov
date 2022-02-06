package com.khair.tinkofflabapp.data.repository

import com.khair.tinkofflabapp.data.remote.GifPostService
import com.khair.tinkofflabapp.presentation.model.GifCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GifPostRepository @Inject constructor(private val gifPostService: GifPostService) {

    suspend fun getGifPost() = withContext(Dispatchers.IO){
        val remoteGifPost = gifPostService.getRandom()
        GifCard(remoteGifPost.url, remoteGifPost.title, remoteGifPost.author)
    }

    suspend fun getList(category: String, page: Int) = withContext(Dispatchers.IO){
        val remoteList = gifPostService.getList(category, page).result
        remoteList.map { GifCard(it.url, it.title, it.author) }
    }
}