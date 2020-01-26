package com.example.tictactoe.di.modules

import android.content.Context
import com.example.tictactoe.app.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun providesApplication(app: App): Context = app
}