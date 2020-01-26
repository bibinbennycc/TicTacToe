package com.example.tictactoe.di.modules

import com.example.tictactoe.feature.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}