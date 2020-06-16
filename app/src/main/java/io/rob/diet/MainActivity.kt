package io.rob.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val controller by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val listener by lazy {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingsFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setupWithNavController(controller)
    }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        controller.removeOnDestinationChangedListener(listener)
    }

    fun updateNavigationBarColorTo(@ColorRes color: Int) {
        binding.bottomNavigation.setBackgroundColor(
            ContextCompat.getColor(this, color)
        )
    }
}