package io.rob.diet.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.common.updateExternalColors
import io.rob.diet.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    private val loginContent = registerForActivityResult(LogInContract()) {
        viewModel.loadUserData()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        binding.btnLogIn.setOnClickListener {
            loginContent.launch(null)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logOut()
        }

        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateExternalColors(R.color.settings_background_color)

        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (it is Lce.Success) {
                showSuccess(it.data)
            } else {
                showLoading()
            }
        })

        viewModel.loadUserData()
    }

    private fun showLoading() {
        binding.userView.visibility = View.GONE
        binding.btnLogIn.visibility = View.GONE
        binding.accountLoading.visibility = View.VISIBLE
    }

    private fun showSuccess(user: User) {
        if (user.isEmpty()) {
            binding.userView.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
            binding.accountLoading.visibility = View.GONE
        } else {
            bind(user)
            binding.userView.visibility = View.VISIBLE
            binding.btnLogIn.visibility = View.GONE
            binding.accountLoading.visibility = View.GONE
        }
    }

    private fun bind(user: User) {
        binding.displayName.text = user.displayName
        binding.avatar.load(user.photoUrl)
    }
}