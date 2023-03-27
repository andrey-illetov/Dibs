package ru.illetov.dibs

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DibsApplication: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}