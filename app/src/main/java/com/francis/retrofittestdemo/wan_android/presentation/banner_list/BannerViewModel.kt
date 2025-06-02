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
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BannerListState()
        )

    private val _event = MutableSharedFlow<BannerEvent>()
    val event = _event.asSharedFlow()

    private fun fetchBanners() {
        viewModelScope.launch {
            _bannerList.update {
                it.copy(isLoading = true)
            }
            getBannerUseCase
                .getBanners()
                .onSuccess { success ->
                    _bannerList.update {
                        it.copy(
                            isLoading = false,
                            banners = success.map { banner ->
                                banner.toBannerUi()
                            }
                        )


                    }
                }
                .onError { error ->
                    _bannerList.update { it.copy(isLoading = false) }
                    _event.emit(BannerEvent.BannerError(error.asUiText()))
                }
        }
    }
}