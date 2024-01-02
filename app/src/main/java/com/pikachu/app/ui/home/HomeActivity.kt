package com.pikachu.app.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.pikachu.app.R
import com.pikachu.app.databinding.ActivityHomeBinding
import com.pikachu.app.ui.home.bottomMenuFragments.ChatFragment
import com.pikachu.app.ui.home.bottomMenuFragments.home.HomeFragment
import com.pikachu.app.ui.home.bottomMenuFragments.MyHubFragment
import java.util.Timer
import java.util.TimerTask
import kotlin.math.absoluteValue

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private var isToolbarAnimating = false
    private var isScrollTimerActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener(navListener)

        // Load the default fragment
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, HomeFragment())
            .commit()
    }

    private val navListener =
        NavigationBarView.OnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            // Handle item selection based on the clicked item ID
            when (item.itemId) {
                R.id.action_home -> selectedFragment = HomeFragment()
                R.id.action_chat -> selectedFragment = ChatFragment()
                R.id.action_hub -> selectedFragment = MyHubFragment()
            }

            // Load the selected fragment
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
                return@OnItemSelectedListener true
            }
            return@OnItemSelectedListener false
        }

    fun scrollToolBar(dy: Int) {
        if (!isScrollTimerActive) {
            isScrollTimerActive = true
            val layoutParams = binding.cstmToolbar.layoutParams as ConstraintLayout.LayoutParams
            val targetMargin: Int
            if (dy > 0) {
                if (dy > layoutParams.height && layoutParams.topMargin.absoluteValue < layoutParams.height) {
                    targetMargin = -layoutParams.height
                    animateToolbar(layoutParams.topMargin, targetMargin)
                } else if (dy <= layoutParams.height && layoutParams.topMargin.absoluteValue < layoutParams.height) {
                    targetMargin =
                        if ((layoutParams.topMargin - dy).absoluteValue > layoutParams.height) -layoutParams.height else layoutParams.topMargin - dy
                    animateToolbar(layoutParams.topMargin, targetMargin)
                }

            } else if (dy < 0) {
                if (dy.absoluteValue > layoutParams.height && layoutParams.topMargin != 0) {
                    targetMargin = 0
                    animateToolbar(layoutParams.topMargin, targetMargin)
                } else if (dy.absoluteValue <= layoutParams.height && layoutParams.topMargin != 0) {
                    targetMargin =
                        if (layoutParams.topMargin + dy.absoluteValue > 0) 0 else layoutParams.topMargin + dy.absoluteValue
                    animateToolbar(layoutParams.topMargin, targetMargin)
                }
            }
            startScrollTimer()
        }
    }

    private fun animateToolbar(startMargin: Int, endMargin: Int) {
        isToolbarAnimating = true

        val animator = ValueAnimator.ofInt(startMargin, endMargin)
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = binding.cstmToolbar.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = animatedValue
            binding.cstmToolbar.layoutParams = layoutParams
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isToolbarAnimating = false
            }
        })

        animator.duration = 100L
        animator.start()
    }
    private fun startScrollTimer() {
        // Ignore subsequent scroll events for a short duration
        val scrollTimer = Timer()
        scrollTimer.schedule(object : TimerTask() {
            override fun run() {
                isScrollTimerActive = false // Timer elapsed, allow new scroll events
                scrollTimer.cancel()
            }
        }, 50L)
    }

}