package com.me.kotlinpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.me.kotlinpractice.databinding.ActivityMainBinding
import com.me.kotlinpractice.databinding.ActivitySearchBinding
import com.me.library.ext.doAfterTextChanged
import com.me.library.ext.doOnTextChanged
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy{
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val stateFlow = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        searchFilter()
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            stateFlow.value = text.toString()
        }

    }

    /**
     * 优化 App 搜索功能
     */
    private fun searchFilter(){
        stateFlow
            .debounce(400)
            .filter {
                it.isNotEmpty()
            }
            .distinctUntilChanged()
            .flatMapLatest {
//                getList(it).asFlow()  逐个返回
//                flow {
//                    emit(getList(it))
//                }
                getFlowList(it)
            }
            .catch { LogUtils.d(it.message) }
            .flowOn(Dispatchers.Default)
            .onEach {
                LogUtils.d("输出:$it")
                binding.tvShow.text = it.toString()
            }.flowOn(Dispatchers.Main)
            .launchIn(lifecycleScope)
    }


    /**
     * 模拟请求
     */
    suspend fun getList(str: String):List<String>{
//        delay(1000)
        return listOf<String>("$str 01","$str 02","$str 03")
    }

    suspend fun getFlowList(str: String):Flow<List<String>>{
//        delay(1000)
        return flow { emit(listOf<String>("$str 01","$str 02","$str 03")) }
    }



}