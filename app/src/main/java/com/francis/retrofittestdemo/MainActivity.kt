package com.francis.retrofittestdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francis.retrofittestdemo.core.presentation.uitl.ObserveAsEvents
import com.francis.retrofittestdemo.ui.theme.RetrofitTestDemoTheme
import com.francis.retrofittestdemo.wan_android.presentation.BannerListScreen
import com.francis.retrofittestdemo.wan_android.presentation.banner_list.BannerEvent
import com.francis.retrofittestdemo.wan_android.presentation.banner_list.BannerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitTestDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 拿到viewmodel
                    val viewModel: BannerViewModel by viewModels()
                    // 收集banners
                    // .collectAsStateWithLifecycle() 自动在合适的生命周期（如 STARTED）时开始收集,在 UI 不可见时（如暂停或销毁）自动停止收集，避免资源浪费和内存泄漏
                    val banners by viewModel.bannerList.collectAsStateWithLifecycle()

                    // 收集事件
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.event) { event ->
                        when (event) {
                            is BannerEvent.BannerError -> {
                                Toast.makeText(
                                    context,
                                    event.error.asString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    BannerListScreen(
                        state = banners,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}