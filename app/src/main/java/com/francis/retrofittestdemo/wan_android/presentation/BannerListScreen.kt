package com.francis.retrofittestdemo.wan_android.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.francis.retrofittestdemo.R
import com.francis.retrofittestdemo.ui.theme.RetrofitTestDemoTheme
import com.francis.retrofittestdemo.wan_android.presentation.banner_list.BannerListState
import com.francis.retrofittestdemo.wan_android.presentation.models.BannerUi

/**
 * UI层显示 Banner 列表
 */
@Composable
fun BannerListScreen(
    state: BannerListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.banners) { banner ->
                AsyncImage(
                    model = banner.imagePath,
                    contentDescription = banner.title,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun BannerListScreenPreview() {
    RetrofitTestDemoTheme {
        BannerListScreen(
            state = onlineBannerState
        )
    }
}


internal val onlineBannerState = BannerListState(
    isLoading = false,
    banners = listOf(
        BannerUi(
            id = 30,
            imagePath = "https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
            title = "我们支持订阅啦~"
        ),
        BannerUi(
            id = 30,
            imagePath = "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            title = "我们新增了一个常用导航Tab~"
        ),
        BannerUi(
            id = 30,
            imagePath = "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
            title = "一起来做个App吧"
        ),
    )
)

internal val localBannerState = BannerListState(
    isLoading = false,
    banners = listOf(
        BannerUi(
            id = 30,
            imagePath = "${R.drawable.test_img1}",
            title = "我们支持订阅啦~"
        ),
        BannerUi(
            id = 30,
            imagePath = "${R.drawable.test_img2}",
            title = "我们新增了一个常用导航Tab~"
        ),
        BannerUi(
            id = 30,
            imagePath = "${R.drawable.test_img3}",
            title = "一起来做个App吧"
        )
    )
)