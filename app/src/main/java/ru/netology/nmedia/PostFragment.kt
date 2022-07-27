package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.IntArg
import ru.netology.nmedia.vewModel.PostViewModel


class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val viewHolder = PostViewHolder(binding.postLayout, object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(R.id.action_postFragment_to_newPostFragment2,
                    Bundle().apply { textArg = post.content })
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.description_sharePost))
                startActivity(shareIntent)
                viewModel.share(post.id)
            }

            override fun onPlayVideo(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }
                val videoIntent =
                    Intent.createChooser(intent, getString(R.string.play))
                startActivity(videoIntent)
            }

            override fun onPostClicked(post: Post) {
                TODO("Not yet implemented")
            }

            override fun onRemoveById(post: Post) {
                viewModel.removeByID(post.id)
                findNavController().navigateUp()
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { posts ->
                posts.firstOrNull{it.id == arguments?.idArg}?.let { viewHolder.bind(it) }
        }

        return binding.root
    }
    companion object {
        var Bundle.idArg: Int? by IntArg
    }
}