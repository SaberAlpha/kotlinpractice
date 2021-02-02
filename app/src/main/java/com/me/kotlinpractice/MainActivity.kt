package com.me.kotlinpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.me.kotlinpractice.databinding.ActivityMainBinding
import com.me.library.ext.countDownCoroutines
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var job:Job?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCount.setOnClickListener {
            job = countTime(0,60,{
                print("倒计时:${it}s")
            },{
                print("倒计时:结束")
            })
        }
        binding.btnFinish.setOnClickListener {
            LogUtils.d("倒计时:开始>>>>")
            if (job != null){
                job?.cancel()
                job = null
                return@setOnClickListener
            }
//            main()
//            counter(Dispatchers.Unconfined,0,5,1000,{
//                LogUtils.d("倒计时>>>$it")
//                binding.tvCount.text = "倒计时:${it}s"
//            },{
//                LogUtils.d("倒计时>>>完成")
//            })
//            GlobalScope.launch (Dispatchers.Main){
//                delay(600)
//                binding.tvCount.text = "调整>>>>"
//            }
            job = countDownCoroutines(12,{
                binding.tvCount.text = "倒计时:${it}s"
            },{
                binding.tvCount.text = "结束"
            })
//           job = flow{
//                   for (i in 10 downTo 0){
//                       emit(i)
//                       LogUtils.d("emit on ${Thread.currentThread().name}")
//                       delay(1000)
//                   }
//               }.flowOn(Dispatchers.Default)
//                   .onCompletion {
//                           print("倒计时:结束")
//                           LogUtils.d("倒计时:结束>>>>")
//                       }
//                       .onEach {
//                           runOnUiThread {
//                               LogUtils.d("倒计时:${it}s>>>>")
//                               binding.tvCount.text = "倒计时:${it}s"
//                           }
//                           GlobalScope.launch (Dispatchers.Main){
//                               LogUtils.d("倒计时:${it}s>>>>")
//                               binding.tvCount.text = "倒计时:${it}s"
//                           }
//                           LogUtils.d("倒计时:${it}s>>>>")
//                           binding.tvCount.text = "倒计时:${it}s"
//
//                       }.flowOn(Dispatchers.Main)
//                   .launchIn(GlobalScope)

        }

//        flow<Int> { emit(0) }.flowOn(Dispatchers.Default)
    }

    fun countTime(start:Int,end:Int,onProgress:(Int)->Unit,onFinish:()->Unit): Job {
        return GlobalScope.launch {
            flow {
                for (i in start..end){
                    emit(i)
                    delay(1000)
                }
            }.onEach {
                onProgress.invoke(it) }
            .onCompletion { onFinish.invoke() }

        }
    }

//    fun main() = GlobalScope.launch(Dispatchers.IO) {
//        flow {
//            for (i in 1..5) {
//                emit(i)
//                LogUtils.d("emit on ${Thread.currentThread().name}")
//                delay(1000)
//            }
//        }.flowOn(Dispatchers.IO)
//                .collect {
////                    binding.tvCount.text = "倒计时:${it}s"
//                    println("${Thread.currentThread().name}: $it")
//                    LogUtils.d("${Thread.currentThread().name}: $it")
//                }
//    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
//            Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
            delay(100)
            LogUtils.d("Emitting $i")
            emit(i) // 发射下一个值
        }
    }.flowOn(Dispatchers.IO) // 在流构建器中改变消耗 CPU 代码上下文的正确方式

    fun main() = GlobalScope.launch(Dispatchers.Main) {
        simple()
                .catch {
                    LogUtils.d(it.message)
                }
                .collect { value ->
            LogUtils.d("Collected $value")
        }
    }

    fun counter(dispatcher: CoroutineContext, start:Int, end:Int, delay:Long,
                onProgress:((value:Int)->Unit), onFinish: (()->Unit)?= null){
        val out = flow<Int> {
            for (i in start..end) {
                emit(i)
                delay(delay)
            }
        }
        GlobalScope.launch {
            withContext(dispatcher) {
                out.collect {
                    onProgress.invoke(it)
                }
                onFinish?.invoke()
            }
        }
    }

}