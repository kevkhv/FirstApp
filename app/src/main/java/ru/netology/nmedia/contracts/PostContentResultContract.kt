package ru.netology.nmedia.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.NewPostContentActivity

class PostContentResultContract : ActivityResultContract<String?, String?>() {

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, NewPostContentActivity::class.java)
            .putExtra(Intent.EXTRA_TEXT,input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}