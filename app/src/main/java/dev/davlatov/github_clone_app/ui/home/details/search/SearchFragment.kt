package dev.davlatov.github_clone_app.ui.home.details.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.adapters.SearchRepositoriesRvAdapter
import dev.davlatov.github_clone_app.adapters.UsersRvAdapter
import dev.davlatov.github_clone_app.data.local.prefs.SharedPrefs
import dev.davlatov.github_clone_app.databinding.FragmentSearchBinding
import dev.davlatov.github_clone_app.models.repositories_search_response.RepositoriesResponse
import dev.davlatov.github_clone_app.models.users_search_response.UsersResponse
import dev.davlatov.github_clone_app.ui.BaseActivity
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.ui.profile.ProfileFragment
import dev.davlatov.github_clone_app.utils.Extensions.fireToast
import dev.davlatov.github_clone_app.utils.Extensions.setUpLayoutManagerToLinearVertical
import dev.davlatov.github_clone_app.utils.Logger
import dev.davlatov.github_clone_app.utils.Status
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapterRepos: SearchRepositoriesRvAdapter
    private lateinit var adapterUsers: UsersRvAdapter

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var _viewLifecycleOwner: LifecycleOwner

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        _viewLifecycleOwner = viewLifecycleOwner
        adapterRepos = SearchRepositoriesRvAdapter()
        adapterUsers = UsersRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearVertical(requireContext())
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        binding.searchEdittext.requestFocus()

        binding.apply {
            searchEdittext.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return@OnEditorActionListener true
                }
                false
            })
        }
        binding.imageViewGoBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun performSearch() {
        val query = binding.searchEdittext.text.toString()

        if (query.isNotEmpty() && query.isNotBlank()) {
            when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioButton1.id -> {
                    showDialog()
                    viewModel.getRepositories(query)
                    setupRepositoriesObserver()
                }
                binding.radioButton2.id -> {
                    showDialog()
                    viewModel.getUsers(query)
                    setupUsersObserver()
                }
                else -> {
                    showDialog()
                    viewModel.getRepositories(query)
                    setupRepositoriesObserver()
                }
            }
        }
    }

    private fun setupUsersObserver() {
        viewModel.usersResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dismissDialog()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        setUsersDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    dismissDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    dismissDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setupRepositoriesObserver() {
        viewModel.repositoriesResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dismissDialog()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        setReposDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    showDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    showDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setReposDataToUI(reposResponse: RepositoriesResponse) {
        adapterRepos.submitList(reposResponse)
        binding.recyclerView.adapter = adapterRepos
    }

    private fun setUsersDataToUI(response: UsersResponse) {
        adapterUsers.submitList(response)
        binding.recyclerView.adapter = adapterUsers
    }
}