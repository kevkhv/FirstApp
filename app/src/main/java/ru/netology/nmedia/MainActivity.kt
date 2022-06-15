package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.vewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorTextView.text = post.author
                countLikesTextView.text = countStringConverter(post.likes)
                countShareTextView.text = countStringConverter(post.countShare)
                contentTextView.text = post.content
                publishedTextView.text = post.published
                likeImageButton.setImageResource(getLikeIconRes(post.likedByMe))
            }
        }
        binding.likeImageButton.setOnClickListener {
            viewModel.like()
        }

        binding.sharePostImageButton.setOnClickListener {
            viewModel.share()
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



