package com.app.floo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.floo.R
import com.app.floo.common.FragmentPagerAdapter
import com.app.floo.common.ItemFragment
import com.app.floo.databinding.ActivityMainBinding
import com.app.floo.extensions.showToast
import com.app.floo.ui.main.graph.GraphFragment
import com.app.floo.ui.main.text.TextFragment
import com.app.floo.utils.Constants.TOPIC_STATUS
import com.app.floo.utils.Constants.TOPIC_WATER_DISTANCE
import com.app.floo.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Floo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
        initStarted()
        initObserveViewModel()
    }

    private fun initStarted() {
        if (!mainViewModel.isMQTTConnected()) mainViewModel.connectToMQTT()
    }

    private fun initObserveViewModel() {
        mainViewModel.getStateMqttConnection().observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    mainViewModel.reqMessageFromPublisher()
                    mainViewModel.subscribeToTopic(TOPIC_STATUS, null)
                    mainViewModel.subscribeToTopic(TOPIC_WATER_DISTANCE, null)
                }
                is Resource.Failure -> showToast(
                    "[Failed] Connect to MQTT caused by ${response.exception?.message}"
                )
                is Resource.Error -> showToast(
                    "[Error] Connect to MQTT caused by ${response.exception.message}"
                )
                is Resource.UnknownError -> showToast(
                    "[UnknownError] Connect to MQTT caused by ${response.exception.message}"
                )
                else -> throw IllegalStateException("Undefined State")
            }
        })

        mainViewModel.getStateMqttDisconnection().observe(this, { response ->
            when (response) {
                is Resource.Success -> showToast(response.message)
                is Resource.Failure -> showToast(
                    "[Failed] disconnect from MQTT caused by ${response.exception?.message}"
                )
                is Resource.Error -> showToast(
                    "[Error] disconnect from MQTT caused by ${response.exception.message}"
                )
                is Resource.UnknownError -> showToast(
                    "[UnknownError] disconnect from MQTT caused by ${response.exception.message}"
                )
                else -> throw IllegalStateException("Undefined State")
            }
        })

        mainViewModel.getStateUnSubscribeTopic().observe(this, { response ->
            when (response) {
                is Resource.Success -> showToast(response.message)
                is Resource.Failure -> showToast(
                    "[Failed] unsubscribe from topic ${response.message} caused by ${response.exception?.message}"
                )
                is Resource.Error -> showToast(
                    "[Error] unsubscribe from topic ${response.message} caused by ${response.exception.message}"
                )
                is Resource.UnknownError -> showToast(
                    "[UnknownError] unsubscribe from topic ${response.message} caused by ${response.exception.message}"
                )
                else -> throw IllegalStateException("Undefined State")
            }
        })

        mainViewModel.getStateSubscribeToTopic().observe(this, { response ->
            when (response) {
                is Resource.Success -> showToast(response.message)
                is Resource.Failure -> showToast(
                    "[Failed] subscribe to topic ${response.message} caused by ${response.exception?.message}"
                )
                is Resource.Error -> showToast(
                    "[Error] subscribe to topic ${response.message} caused by ${response.exception.message}"
                )
                is Resource.UnknownError -> showToast(
                    "[UnknownError] subscribe from topic ${response.message} caused by ${response.exception.message}"
                )
                else -> throw IllegalStateException("Undefined State")
            }
        })
    }

    private fun initViewPager() {
        with(binding) {
            fragmentPagerAdapter =
                FragmentPagerAdapter(supportFragmentManager, generateMainFragment())
            mainViewPager.adapter = fragmentPagerAdapter
            mainTabLayout.setupWithViewPager(mainViewPager)
        }
    }

    private fun generateMainFragment(): List<ItemFragment> = listOf(
        ItemFragment(
            title = "Angka",
            fragment = TextFragment()
        ),
        ItemFragment(
            title = "Grafik",
            fragment = GraphFragment()
        ),
    )

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.unSubscribeFromTopic(TOPIC_STATUS)
        mainViewModel.unSubscribeFromTopic(TOPIC_WATER_DISTANCE)
        mainViewModel.disconnectFromMQTT()
    }

}