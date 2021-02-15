package com.app.floo.ui.main.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.floo.databinding.FragmentTextBinding
import com.app.floo.extensions.asUnit
import com.app.floo.extensions.setTextColor
import com.app.floo.extensions.showToast
import com.app.floo.extensions.toLowerCase
import com.app.floo.ui.main.MainViewModel
import com.app.floo.ui.main.StatusState
import com.app.floo.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextFragment : Fragment() {

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        mainViewModel.getStateMessageFromPublisher().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.ConnectionLost -> response.exception?.message?.let { showToast(it) }
                is Resource.Error -> response.exception.message?.let { showToast(it) }
                is Resource.UnknownError -> response.exception.message?.let { showToast(it) }
                else -> throw IllegalStateException("Undefined state")
            }
        })

        mainViewModel.getStateStatusTopicMessage().observe(viewLifecycleOwner, { status ->
            displayStatus(status)
        })

        mainViewModel.getStateWaterDistanceTopicMessage().observe(viewLifecycleOwner, { waterDistance ->
            displayWaterDistance(waterDistance)
        })
    }

    private fun displayWaterDistance(value: String) {
        binding.tvWaterDistance.text = value.asUnit("cm")
    }

    private fun displayStatus(status: String) {
        val statusState = when (status.toLowerCase()) {
            StatusState.DANGER.value -> StatusState.DANGER
            StatusState.WARNING.value -> StatusState.WARNING
            StatusState.SAFE.value -> StatusState.SAFE
            else -> throw IllegalStateException("Undefined status state")
        }

        binding.tvStatus.text = status
        binding.tvStatus.setTextColor(statusState.hexColor)

    }

}