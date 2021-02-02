package com.me.library.ext

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 倒计时
 */
fun countDownCoroutines(total:Int,onTick:(Int)->Unit,onFinish:()->Unit,
                        scope: CoroutineScope = GlobalScope):Job{
    return flow{
        for (i in total downTo 0){
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
        .onCompletion { onFinish.invoke() }
        .onEach { onTick.invoke(it) }
        .flowOn(Dispatchers.Main)
        .launchIn(scope)
}