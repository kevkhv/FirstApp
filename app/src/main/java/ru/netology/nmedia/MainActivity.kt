package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.vewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }
            override fun onRemoveById(post: Post) {
                viewModel.removeByID(post.id)
            }
        })
        with(binding) {
            list.adapter = adapter
            saveImageButton.setOnClickListener {
                with(contentEditText) {
                    if (text.isNullOrBlank()) {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.errorEmptyMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    viewModel.changeContent(text.toString())
                    viewModel.save()
                    editConstraintLayout.visibility = View.GONE
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) { post ->
            if (post.id == 0) {
                return@observe
            }
            with(binding) {
                contentEditText.requestFocus()
                contentEditText.setText(post.content)
                contentEditText.setSelection(contentEditText.length())
                lastContentTextView.text = post.content
                editConstraintLayout.visibility = View.VISIBLE
                cancelImageButton.setOnClickListener {
                    viewModel.cancelEdit()
                    editConstraintLayout.visibility = View.GONE
                    contentEditText.setText("")
                    contentEditText.clearFocus()
                    AndroidUtils.hideKeyboard(editConstraintLayout)
                }
            }
        }
    }
}






