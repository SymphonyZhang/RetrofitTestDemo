package com.francis.retrofittestdemo.uilayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.francis.retrofittestdemo.ui.theme.RetrofitTestDemoTheme
import com.francis.retrofittestdemo.uilayer.viewmodel.BannerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitTestDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    TestContent(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun TestContent(viewModel: BannerViewModel = viewModel(), modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(onClick = {
            viewModel.fetchBanners()
        }) {
            Text("Click")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitTestDemoTheme {
        TestContent(modifier = Modifier.fillMaxSize())
    }
}