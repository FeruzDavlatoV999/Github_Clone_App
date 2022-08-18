package dev.davlatov.github_clone_app.ui.explore.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.adapters.ForYouAndTrendingAdapter
import dev.davlatov.github_clone_app.databinding.FragmentForYouBinding
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.utils.Extensions.setUpColors

@AndroidEntryPoint
class ForYouFragment : BaseFragment() {

    private lateinit var binding: FragmentForYouBinding
    private lateinit var adapter: ForYouAndTrendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = ForYouAndTrendingAdapter()
        adapter.submitList(prepareModelsList())

        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            swipeRefreshLayout.setUpColors(requireContext())
        }
    }
}
