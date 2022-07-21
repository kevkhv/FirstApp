package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.vewModel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg?.let(binding.editTextView::setText)

        with(binding) {
            editTextView.requestFocus()
            okActionButton.setOnClickListener {
                if (!editTextView.text.isNullOrBlank()) {
                    val content = editTextView.text.toString()
                    viewModel.changeContent(content)
                    viewModel.save()
                }
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }
}