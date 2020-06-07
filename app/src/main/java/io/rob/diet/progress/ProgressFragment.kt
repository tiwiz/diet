package io.rob.diet.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.common.updateExternalColors
import io.rob.diet.databinding.FragmentProgressBinding

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding

    private val viewModel by viewModels<ProgressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateExternalColors(R.color.progress_background_color)

        binding.btnAdd.setOnClickListener {
            val action = ProgressFragmentDirections.actionProgressFragmentToMeasurementFragment()
            findNavController().navigate(action)
        }
    }
}