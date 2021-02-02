package com.me.kotlinpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
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
        setContentView(binding.root)

        binding.tvCount.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
            finish()
        }
        binding.btnFinish.setOnClickListener {
            LogUtils.d("倒计时:开始>>>> ")
            if (job != null){
                job?.cancel()
                job = null
                return@setOnClickListener
            }
            job = countDownCoroutines(5,{
                binding.tvCount.text = "倒计时:${it}s"
            },{
                binding.tvCount.text = "结束"
            },lifecycleScope)

        }
    }

}