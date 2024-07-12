package com.thinkwarecleanarchitecture

import android.app.Application
import com.tca.core.repos.LocalDbRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class TCAApplication :
    Application(),
    KoinComponent {
    private val localDbRepository by inject<LocalDbRepository>()

    override fun onCreate() {
        super.onCreate()
        localDbRepository.init(applicationContext)
    }
}
