package com.app.floo.ui.main.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.floo.R
import com.app.floo.databinding.FragmentGraphBinding
import com.app.floo.extensions.setTextColor
import com.app.floo.extensions.showToast
import com.app.floo.extensions.toLowerCase
import com.app.floo.ui.main.MainViewModel
import com.app.floo.ui.main.StatusState
import com.app.floo.vo.Resource
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GraphFragment : Fragment(), OnChartValueSelectedListener {

    companion object {
        const val WATER_DISTANCE_DATA_SET_INDEX = 0
    }

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var valueTimeFormatter: HourAxisValueFormatter

    private val waterDistanceLineData: LineData by lazy {
        mainViewModel.getWaterDistanceLineData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLineChart()
        setupWaterDistanceLineChartDataSet()
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        mainViewModel.getStateMessageFromPublisher().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.ConnectionLost -> response.exception?.message?.let { showToast(it) }
                is Resource.Error -> response.exception.message?.let { showToast(it) }
                is Resource.UnknownError -> response.exception.message?.let { showToast(it) }
                else -> throw IllegalStateException("Undefined message state")
            }
        })

        mainViewModel.getStateStatusTopicMessage().observe(viewLifecycleOwner, { status ->
            displayStatus(status)
        })

        mainViewModel.getStateWaterDistanceUpdateChart().observe(viewLifecycleOwner, { entryCount ->
            updateChartView(entryCount.toFloat())
        })

        // Executed only once after first data received
        mainViewModel.getStateReferenceEpochTimestamp()
            .observe(viewLifecycleOwner, { referenceEpochTimeStamp ->
                valueTimeFormatter.setReferenceEpochTimestamp(referenceEpochTimeStamp)
            })

    }

    private fun displayStatus(response: String) {
        val statusState = when (response.toLowerCase()) {
            StatusState.DANGER.value -> StatusState.DANGER
            StatusState.WARNING.value -> StatusState.WARNING
            StatusState.SAFE.value -> StatusState.SAFE
            else -> throw IllegalStateException("Undefined status state")
        }

        binding.tvStatus.text = response
        binding.tvStatus.setTextColor(statusState.hexColor)

    }

    private fun setupLineChart() {
        valueTimeFormatter = HourAxisValueFormatter()
        binding.lineChart.apply {
            setOnChartValueSelectedListener(this@GraphFragment)
            description.isEnabled = false
            setTouchEnabled(true)
            setDrawGridBackground(false)
            setPinchZoom(true)
            setBackgroundColor(Color.WHITE)
            setMaxVisibleValueCount(60)
            xAxis.valueFormatter = valueTimeFormatter
        }.also {
            waterDistanceLineData.setValueTextColor(Color.DKGRAY)
            it.data = waterDistanceLineData
        }
    }

    private fun createLineDataSet(): LineDataSet = LineDataSet(
        null,
        resources.getString(R.string.label_realtime_graph)
    ).apply {
        axisDependency = YAxis.AxisDependency.LEFT
        color = ColorTemplate.getHoloBlue()
        setCircleColor(Color.DKGRAY)
        lineWidth = 2f
        circleRadius = 4f
        fillAlpha = 65
        fillColor = ColorTemplate.getHoloBlue()
        highLightColor = Color.rgb(244, 117, 117)
        valueTextColor = Color.DKGRAY
        valueTextSize = 9f
        setDrawValues(false)
    }

    private fun setupWaterDistanceLineChartDataSet() {
        val lineData = binding.lineChart.data
        lineData?.let { data ->
            var set = data.getDataSetByIndex(WATER_DISTANCE_DATA_SET_INDEX)

            if (set == null) {
                set = createLineDataSet()
                data.addDataSet(set)
            }
        }
    }

    private fun updateChartView(entryCount: Float) {
        binding.lineChart.notifyDataSetChanged()
        binding.lineChart.moveViewToX(entryCount)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        val waterDistance = e?.y ?: 0F
        val actualTime = e?.x ?: 0F
        val formattedActualTime = valueTimeFormatter.getFormattedHour(actualTime)
        showToast(
            resources.getString(
                R.string.toast_water_distance_chart_value,
                waterDistance,
                formattedActualTime
            )
        )
    }

    override fun onNothingSelected() {}

}