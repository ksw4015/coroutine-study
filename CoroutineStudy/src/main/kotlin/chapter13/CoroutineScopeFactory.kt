package org.example.chapter13

import kotlinx.coroutines.*

/**
 * Android ViewModel 에서 CoroutineScope 만들기
 */
abstract class ViewModel {
    protected open fun onCleared() {}
}

abstract class BaseViewModel(
    private val onError: (Throwable) -> Unit
) : ViewModel() {
    // 잡히지 않는 예외를 처리하는 방법
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
    // 코루틴을 독립적으로 작동하게 하기위해 SupervisorJob 사용
    private val context =
        Dispatchers.Main + SupervisorJob() + exceptionHandler

    protected val scope =
        CoroutineScope(context)

    // Activity onDestroy() 와 비슷하게 ViewModel 의 onCleared 메서드에서 코루틴 취소
    override fun onCleared() {
        scope.coroutineContext.cancelChildren()
    }
}