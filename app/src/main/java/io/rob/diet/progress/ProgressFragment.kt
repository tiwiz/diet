package io.rob.diet.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.common.updateExternalColors
import io.rob.diet.databinding.FragmentProgressBinding
import io.rob.diet.measurement.MeasureViewModel

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding

    private val viewModel by viewModels<ProgressViewModel>()
    private val sharedViewModel by activityViewModels<MeasureViewModel>()

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

        viewModel.recap.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Lce.Loading -> onLoading()
                is Lce.Success -> onSuccess(it.data)
                else -> onError()
            }
        })

        sharedViewModel.status.observe(viewLifecycleOwner, Observer {
            viewModel.fetchRecap()
        })
    }

    private fun onLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.emptyProgress.visibility = View.GONE
        binding.progressView.visibility = View.GONE
    }

    private fun onError() {
        binding.loading.visibility = View.GONE
        binding.emptyProgress.visibility = View.VISIBLE
        binding.progressView.visibility = View.GONE
    }

    private fun onSuccess(recap: RecapUI) {
        binding.loading.visibility = View.GONE
        binding.emptyProgress.visibility = View.GONE
        binding.progressView.visibility = View.VISIBLE
        binding.progressView.bind(recap)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchRecap()
    }
}