package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostContentBinding

class NewPostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            editTextView.requestFocus()
            if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                editTextView.setText(text)
                editTextView.setSelection(editTextView.length())
            }

            okActionButton.setOnClickListener {
                val newIntent = Intent()
                if (editTextView.text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, newIntent)
                } else {
                    val content = editTextView.text.toString()
                    newIntent.putExtra(Intent.EXTRA_TEXT, content)
                    setResult(Activity.RESULT_OK, newIntent)
                }
                finish()
            }
        }
    }
}