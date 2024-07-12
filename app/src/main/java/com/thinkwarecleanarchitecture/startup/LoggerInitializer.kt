package com.thinkwarecleanarchitecture.startup

import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val formatStrategy: FormatStrategy =
            PrettyFormatStrategy
                .newBuilder()
                .tag("TMDBLogger")
                .build()
        return Logger.addLogAdapter(
            object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(
                    priority: Int,
                    tag: String?,
                ): Boolean = true
            },
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
