package dev.davlatov.github_clone_app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.databinding.FragmentHomeBinding
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.utils.Extensions.setUpColors


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            swipeRefreshLayout.setUpColors(requireContext())

            itemRepositories.setOnClickListener {
                findNavController().navigate(R.id.repositoriesFragment)
            }

            imageViewSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }
        }

    }
}
