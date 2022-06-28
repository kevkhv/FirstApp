package ru.netology.nmedia.adapter

import android.widget.EditText
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.utils.AndroidUtils

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener : OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            authorTextView.text = post.author
            countLikesTextView.text = countStringConverter(post.likes)
            contentTextView.text = post.content
            publishedTextView.text = post.published
            likeImageButton.setImageResource(getLikeIconRes(post.likedByMe))
            likeImageButton.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            countShareTextView.text = countStringConverter(post.countShare)
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

    private fun getLikeIconRes(liked: Boolean) =
        if (!liked) R.drawable.ic_baseline_favorite_border_24
        else R.drawable.ic_baseline_favorite_24
}