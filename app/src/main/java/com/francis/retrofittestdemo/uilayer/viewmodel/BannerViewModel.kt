package com.francis.retrofittestdemo.uilayer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francis.retrofittestdemo.datalayer.repository.BannerRepository
import kotlinx.coroutines.launch

class BannerViewModel:ViewModel() {
    companion object{
        private const val TAG = "zyx"
    }

    private val repository = BannerRepository()

    fun fetchBanners(){
        viewModelScope.launch {
            val result = repository.getBanners()
            // result 的打印结果如下:
            //Success(BannerData(data=[Banner(desc=我们支持订阅啦~, id=30, imagePath=https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png, isVisible=1, order=2, title=我们支持订阅啦~, type=0, url=https://www.wanandroid.com/blog/show/3352), Banner(desc=, id=6, imagePath=https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png, isVisible=1, order=1, title=我们新增了一个常用导航Tab~, type=1, url=https://www.wanandroid.com/navi), Banner(desc=一起来做个App吧, id=10, imagePath=https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png, isVisible=1, order=1, title=一起来做个App吧, type=1, url=https://www.wanandroid.com/blog/show/2)], errorCode=0, errorMsg=))
            Log.w(TAG, "fetchBanners: ==> start" )
            Log.i(TAG, "fetchBanners: ==> $result" )
            Log.w(TAG, "fetchBanners: ==> end" )
        }
    }
}