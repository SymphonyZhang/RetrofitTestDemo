package com.francis.retrofittestdemo.datalayer.repository

import com.francis.retrofittestdemo.datalayer.network.Banner
import com.francis.retrofittestdemo.datalayer.network.BannerData
import com.francis.retrofittestdemo.datalayer.network.RetrofitClient

class BannerRepository {
    private val api = RetrofitClient.instance

    suspend fun getBanners():Result<BannerData<List<Banner>>>{
        return try {
            val response = api.getBanner()
            if(response.errorCode == 0){
                Result.success(response)
            }else{
                Result.failure(Exception(response.errorMsg))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}