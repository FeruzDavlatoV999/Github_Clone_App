package dev.davlatov.github_clone_app.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.davlatov.github_clone_app.ui.explore.details.ForYouFragment
import dev.davlatov.github_clone_app.ui.explore.details.TrendingFragment

// this is a viewpager2 adapter for explore page
class ExploreViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2
    override fun createFragment(position: Int) =
        if (position == 0) ForYouFragment() else TrendingFragment()
}
