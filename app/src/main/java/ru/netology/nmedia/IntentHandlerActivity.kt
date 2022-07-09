package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_SEND) return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (text.isNullOrBlank()) {
            Snackbar.make(binding.root, R.string.errorEmptyMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) {
                    finish()
                }.show()
        } else {
            binding.incomingTextTextView.text = text
        }
    }
}