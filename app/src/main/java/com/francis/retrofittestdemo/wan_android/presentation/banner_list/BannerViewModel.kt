package com.francis.retrofittestdemo.wan_android.presentation.banner_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francis.retrofittestdemo.core.domain.util.onError
import com.francis.retrofittestdemo.core.domain.util.onSuccess
import com.francis.retrofittestdemo.core.presentation.uitl.asUiText
import com.francis.retrofittestdemo.wan_android.domain.GetBannersUseCase
import com.francis.retrofittestdemo.wan_android.presentation.models.toBannerUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BannerViewModel(
    private val getBannerUseCase: GetBannersUseCase = GetBannersUseCase() // 通过domain层的UseCase进行数据获取
) : ViewModel() {

    private val _bannerList = MutableStateFlow(BannerListState())
    val bannerList = _bannerList
        //首次收集就执行这个方法
        .onStart { //首次收集就执行这个方法
            fetchBanners()
        }
        // 转为stateFlow
        .stateIn(
            scope = viewModelScope, //作用域，跟viewModel的生命周期绑定
            started = SharingStarted.WhileSubscribed(5000L), //策略:切换到其他地方去了，5秒内还不回来，这个热流就会停止，下次回来再次重新启动，如果5秒内切回来了，这个流就会继续保持着不会停止
            initialValue = BannerListState() //初始化
        )

    private val _event = MutableSharedFlow<BannerEvent>()
    val event = _event.asSharedFlow()

    /**
     * 获取Banner列表
     * Banner类型转为BannerUi类型
     */
    private fun fetchBanners() {
        viewModelScope.launch {
            _bannerList.update {
                it.copy(isLoading = true) // 加载先显示加载圈
            }
            getBannerUseCase
                .getBanners()
                .onSuccess { success ->
                    _bannerList.update { // 成功拿到了数据，关闭加载圈并更新数据
                        it.copy(
                            isLoading = false,
                            banners = success.map { banner ->
                                banner.toBannerUi()
                            }
                        )


                    }
                }
                .onError { error -> // 拿不到就把加载圈关闭，并把error对应的错误信息传递到收集端
                    _bannerList.update { it.copy(isLoading = false) }
                    _event.emit(BannerEvent.BannerError(error.asUiText()))
                }
        }
    }
}