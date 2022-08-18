package dev.davlatov.github_clone_app.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.databinding.FragmentNotificationBinding
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.utils.Extensions.setUpColors


@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.swipeRefreshLayout.setUpColors(requireContext())
    }
}

