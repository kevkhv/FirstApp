package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            likes = 11000,
            countShare = 9990,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = true
        )
        with(binding) {
            authorTextView.text = post.author
            countLikesTextView.text = countStringConverter(post.likes)
            countShareTextView.text = countStringConverter(post.countShare)
            contentTextView.text = post.content
            publishedTextView.text = post.published
            likeImageButton.setImageResource(getLikeIconRes(post.likedByMe))

            likeImageButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likeImageButton.setImageResource(getLikeIconRes(post.likedByMe))
                if (post.likedByMe) post.likes++ else post.likes--
                countLikesTextView.text = countStringConverter(post.likes)
            }

            sharePostImageButton.setOnClickListener {
                post.countShare++
                countShareTextView.text = countStringConverter(post.countShare)
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


