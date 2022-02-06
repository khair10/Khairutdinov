package com.khair.tinkofflabapp.presentation.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khair.tinkofflabapp.databinding.ItemGifcardBinding
import com.khair.tinkofflabapp.presentation.model.GifCard

class GifCardAdapter() : RecyclerView.Adapter<GifCardAdapter.GifCardViewHolder>() {

    private var _gifCards: MutableList<GifCard> = ArrayList()
    private val gifCards: List<GifCard>
        get() = _gifCards

    class GifCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(gifCard: GifCard) {
            val binding = ItemGifcardBinding.bind(itemView)
            binding.tvAuthor.text = gifCard.author
            binding.tvTitle.text = gifCard.title
            Glide.with(itemView.context)
                .asGif()
                .load(gifCard.url)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifCardViewHolder {
        val binding = ItemGifcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifCardViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GifCardViewHolder, position: Int) {
        holder.bind(gifCards[position])
    }

    override fun getItemCount(): Int {
        return gifCards.size
    }

    fun add(gifCard: GifCard) {
        _gifCards.add(gifCard)
        notifyItemInserted(_gifCards.size - 1)
    }

    fun addAll(list: List<GifCard>) {
        _gifCards.clear()
        _gifCards.addAll(list)
        notifyDataSetChanged()
    }
}