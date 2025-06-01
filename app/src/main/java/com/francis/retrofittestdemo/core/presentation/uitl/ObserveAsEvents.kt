package com.francis.retrofittestdemo.core.presentation.uitl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


/**
 * 观察并处理一次性事件流， 配合Flow使用
 *   ```
 *   //ViewModel中
 *      ...
 *      private val _event = MutableSharedFlow<DataClass>()
 *      val event = _event.asSharedFlow()
 *      ...
 *      // 或者
 *      ...
 *      private val _events = Channel<DataClass>()
 *      val events = _events.receiveAsFlow()
 *      ...
 *
 *   ```
 *   ```
 *   //在其他地方调用，如Activity
 *      val context = LocalContext.current
 *      ObserveAsEvents(events = viewModel.events) { event ->
 *         //...
 *      }
 *   ```
 * 如:导航，弹窗，Toast
 */
@Composable
fun <T> ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    //获取当前的 LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current
    //启动协程 ，当 lifecycle 变化时会重新启动 LaunchedEffect
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        // 在生命周期处于 STARTED 状态时启动协程块，当生命周期低于 STARTED 时自动暂停收集（取消协程），重新进入 STARTED 时再恢复
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // 确保事件在主线程处理 immediate：若已在主线程则立即执行（避免不必要的线程切换）
            withContext(Dispatchers.Main.immediate) {
                // 开始收集 Flow 中的事件，并对每个事件调用
                events.collect(onEvent)
            }
        }
    }
}