package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener : OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            authorTextView.text = post.author
            contentTextView.text = post.content
            publishedTextView.text = post.published
            likeImageButton.text = countStringConverter(post.likes)
            likeImageButton.isChecked = post.likedByMe
            likeImageButton.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            sharePostImageButton.text = countStringConverter(post.countShare)
            sharePostImageButton.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            menuImageButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemoveById(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    private fun countStringConverter(value: Int): String {
        return when {
            value / 1000 == 0 -> value.toString()
            value / 1000 in 1..9 -> "${(value / 1000)}.${(value % 1000 / 100)}К"
            value / 1000 in 10..999 -> "${(value / 1000)}К"
            else -> "${(value / 1000000)}.${(value % 1000000 / 100000)}M"
        }
    }
}