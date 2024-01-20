package com.bonge.traveltest.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

enum class Language(val value: String) {
    繁體中文("zh-tw"),
    簡體中文("zh-cn"),
    英文("en"),
    日文("ja"),
    韓文("ko"),
    西班牙文("es"),
    印尼文("id"),
    泰文("th"),
    越南文("vi")
}

object Util {
    fun setLocale(context: Context?, language: Language) {
        context?.let {
            val locale = if (language == Language.簡體中文) {
                Locale(Locale.CHINA.language)
            } else {
                Locale(language.value)
            }
            val newConfig = Configuration()
            newConfig.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val configurationContext = context.createConfigurationContext(newConfig)
                context.resources.updateConfiguration(
                    configurationContext.resources.configuration,
                    context.resources.displayMetrics
                )
            } else {
                context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)
            }
        }

    }

}