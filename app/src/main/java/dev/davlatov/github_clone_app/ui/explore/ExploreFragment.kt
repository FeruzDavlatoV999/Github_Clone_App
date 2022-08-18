package dev.davlatov.github_clone_app.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.adapters.ExploreViewPagerAdapter
import dev.davlatov.github_clone_app.databinding.FragmentExploreBinding
import dev.davlatov.github_clone_app.ui.BaseFragment

@AndroidEntryPoint
class ExploreFragment : BaseFragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter: ExploreViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = ExploreViewPagerAdapter(requireActivity())

        binding.apply {
            viewPager2.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position -> // Styling each tab here
                tab.text =
                    if (position == 0) getString(R.string.txt_for_you).uppercase()
                    else getString(R.string.txt_trending).uppercase()
            }.attach()

            viewPager2.apply {
                (getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            }
        }
    }
}
