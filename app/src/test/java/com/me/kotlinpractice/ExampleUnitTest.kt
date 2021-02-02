package com.me.kotlinpractice

import com.blankj.utilcode.util.LogUtils
import com.me.kotlinpractice.entities.User
import com.me.library.ext.fromJson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//        val user = fromJson<List<User>>("""[{"name":"张三","sex":2},{"name":"李四","sex":2}]""")
//        print(user.toString())
//        countTime(0,60,{
//            print("倒计时:${it}s")
//        },{
//            print("倒计时:结束")
//        })
    }

    fun countTime(start:Int,end:Int,onProgress:(Int)->Unit,onFinish:()->Unit): Job {
        return GlobalScope.launch {
            flow {
                for (i in start..end){
                    emit(i)
                    delay(1000)
                }
            }.onEach {

                 }
                .onCompletion { onFinish.invoke() }

        }
    }
}