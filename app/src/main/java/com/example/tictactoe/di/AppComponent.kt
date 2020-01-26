package com.example.tictactoe.di

import com.example.tictactoe.app.App
import com.example.tictactoe.di.modules.ActivityBuildersModule
import com.example.tictactoe.di.modules.AppModule
import com.example.tictactoe.di.modules.FragmentBuildersModule
import com.example.tictactoe.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AppModule::class, ActivityBuildersModule::class,
    FragmentBuildersModule::class,
    ViewModelModule::class))
interface AppComponent  {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}