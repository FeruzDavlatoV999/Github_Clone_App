package dev.davlatov.github_clone_app.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.adapters.ProfileRepositoriesRvAdapter
import dev.davlatov.github_clone_app.data.local.prefs.SharedPrefs
import dev.davlatov.github_clone_app.databinding.FragmentProfileBinding
import dev.davlatov.github_clone_app.models.User
import dev.davlatov.github_clone_app.models.user_repos_response.UserRepositoriesResponseItem
import dev.davlatov.github_clone_app.models.users_search_response.UsersResponse
import dev.davlatov.github_clone_app.ui.BaseActivity
import dev.davlatov.github_clone_app.ui.BaseFragment
import dev.davlatov.github_clone_app.utils.Extensions.fireToast
import dev.davlatov.github_clone_app.utils.Extensions.setUpLayoutManagerToLinearHorizontal
import dev.davlatov.github_clone_app.utils.Logger
import dev.davlatov.github_clone_app.utils.Status
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: ProfileRepositoriesRvAdapter

    private val viewModel by viewModels<ProfileViewModel>()
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        _viewLifecycleOwner = viewLifecycleOwner
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        adapter = ProfileRepositoriesRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearHorizontal(requireContext())
        viewModel.getUserAllData(sharedPrefs.accessToken!!)
        setupObserver()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserAllData(sharedPrefs.accessToken!!)
        }
    }

    private fun setupObserver() {
        viewModel.userAllData.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dismissDialog()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { triple -> setDataToUI(triple) }
                }
                Status.LOADING -> {
                    showDialog()
                }
                Status.ERROR -> {
                    dismissDialog()
                    Logger.d(TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setDataToUI(triple: Triple<UsersResponse, List<UserRepositoriesResponseItem>, User>) {
        val usersResponse = triple.first
        val userRepositoriesResponse = triple.second
        val user = triple.third
        val firstUser = usersResponse.items?.get(0)

        binding.apply {
            Glide.with(binding.root.context).load(firstUser?.avatarUrl).into(imageViewProfile)
            textViewFullname.text = user.name
            textViewUsername.text = firstUser?.login
            textViewBio.text = user.bio
            textViewLocation.text = "\uD83D\uDCCD" + user.location
            textViewFollowersFollowing.text =
                "Ⓒ ${user.followers} followers • ${user.following} following"
            textViewCountOfRepositories.text =
                userRepositoriesResponse.size.toString()

        }

        adapter.submitList(userRepositoriesResponse)
        binding.recyclerView.adapter = adapter
    }
}
