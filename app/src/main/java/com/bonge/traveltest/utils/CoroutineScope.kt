package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun withMain(unit: () -> Unit) {
    withContext(Dispatchers.Main) {
        unit.invoke()
    }
}

fun launch(unit: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        unit.invoke()
    }
}


