package dev.davlatov.github_clone_app.ui.home.details.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.adapters.UserRepositoriesRvAdapter
import dev.davlatov.github_clone_app.data.local.prefs.SharedPrefs
import dev.davlatov.github_clone_app.databinding.FragmentRepositoriesBinding
import dev.davlatov.github_clone_app.models.user_repos_response.UserRepositoriesResponseItem
import dev.davlatov.github_clone_app.ui.BaseActivity
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.ui.profile.ProfileFragment
import dev.davlatov.github_clone_app.utils.Extensions.fireToast
import dev.davlatov.github_clone_app.utils.Extensions.setUpColors
import dev.davlatov.github_clone_app.utils.Extensions.setUpLayoutManagerToLinearVertical
import dev.davlatov.github_clone_app.utils.Logger
import dev.davlatov.github_clone_app.utils.Status
import javax.inject.Inject


@AndroidEntryPoint
class RepositoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentRepositoriesBinding
    private lateinit var adapter: UserRepositoriesRvAdapter

    private val viewModel by viewModels<RepositoriesViewModel>()
    private lateinit var _viewLifecycleOwner: LifecycleOwner

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        _viewLifecycleOwner = viewLifecycleOwner
        adapter = UserRepositoriesRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearVertical(requireContext())
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        viewModel.getUserRepositories(sharedPrefs.accessToken!!)
        setupObserver()
        binding.apply {
            swipeRefreshLayout.setUpColors(requireContext())
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getUserRepositories(sharedPrefs.accessToken!!)
            }
        }
        binding.imageViewGoBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        viewModel.userRepositories.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dismissDialog()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { response ->
                        setDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    showDialog()
                }
                Status.ERROR -> {
                    //Handle Error
                    dismissDialog()
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setDataToUI(response: List<UserRepositoriesResponseItem>) {
        adapter.submitList(response)
        binding.recyclerView.adapter = adapter
    }
}

