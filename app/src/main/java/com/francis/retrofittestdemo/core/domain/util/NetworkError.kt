package com.francis.retrofittestdemo.core.domain.util


/**
 * 网络调用的Error类型
 */
enum class NetworkError : Error {
    REQUEST_TIMEOUT,    //请求超时
    TOO_MANY_REQUESTS,  //短时间请求次数过多
    NO_INTERNET,        //没网络
    SERVER_ERROR,       //服务器方错误
    SERIALIZATION,      //数据解析，序列化错误
    UNKNOWN,            //未知错误
}