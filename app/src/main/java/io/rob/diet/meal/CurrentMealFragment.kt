package io.rob.diet.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.rob.diet.databinding.FragmentCurrentMealBinding

class CurrentMealFragment : Fragment() {

    private lateinit var binding: FragmentCurrentMealBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentMealBinding.inflate(inflater, container, false)
        return binding.root
    }
}