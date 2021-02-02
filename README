# kotlinPractice
## Kotlin Flow 应用技巧实践
一. 倒计时
```
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
```

二. Gson拓展
```
 inline fun <reified T> fromJson(json: String): T? {
     return try {
         val type = object : TypeToken<T>() {}.type
         return Gson().fromJson(json, type)
     } catch (e: Exception) {
         null
     }
 }

 fun Any.toJson():String{
     return Gson().toJson(this)
 }
```
