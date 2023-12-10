package com.example.androidexam

import android.app.Application
import com.example.androidexam.data.AppContainer
import com.example.androidexam.data.DefaultAppContainer


class QuizApplication: Application() {
        lateinit var container: AppContainer

        override fun onCreate() {
                super.onCreate()
                container = DefaultAppContainer(context = applicationContext)
        }
}