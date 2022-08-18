package dev.davlatov.github_clone_app.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.databinding.ActivityMainBinding
import dev.davlatov.github_clone_app.ui.explore.ExploreFragment
import dev.davlatov.github_clone_app.ui.explore.details.ForYouFragment
import dev.davlatov.github_clone_app.ui.explore.details.TrendingFragment
import dev.davlatov.github_clone_app.ui.home.HomeFragment
import dev.davlatov.github_clone_app.ui.notification.NotificationFragment
import dev.davlatov.github_clone_app.ui.profile.ProfileFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setupWithNavController(navController)
        workWithBottomNavView()
    }

    // this function does not cause the bottom navigation view UI work strangely but it does not work with animation
    private fun setupNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.notificationFragment -> showBottomNav()
                // and more
                else -> hideBottomNav()
            }
        }
    }


    // this function causes the bottom navigation view UI work strangely
    private fun workWithBottomNavView() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fragmentManager: FragmentManager,
                fragment: Fragment,
                view: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    Slide(Gravity.BOTTOM).excludeTarget(R.id.nav_host_fragment, true)
                )
                when (fragment) {
                    is HomeFragment -> {
                        showBottomNav()
                    }
                    is NotificationFragment -> {
                        showBottomNav()
                    }
                    is ExploreFragment -> {
                        showBottomNav()
                    }
                    is ProfileFragment -> {
                        showBottomNav()
                    }
                    is ForYouFragment -> {
                        showBottomNav()
                    }
                    is TrendingFragment -> {
                        showBottomNav()
                    }
                    else -> {
                        hideBottomNav()
                    }
                }
            }
        }, true)
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE

    }
}