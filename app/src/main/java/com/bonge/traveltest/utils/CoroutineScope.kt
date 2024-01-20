package utils

import android.content.Context
import com.bonge.traveltest.dialog.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun Context.loading(unit: suspend () -> Unit = {}) {
    val loadingDialog = LoadingDialog(this@loading)
    loadingDialog.show()
    CoroutineScope(Dispatchers.IO).launch {
        unit.invoke()
        withContext(Dispatchers.Main) {
            loadingDialog.dismiss()
        }
    }
}

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


