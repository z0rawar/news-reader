package com.monzo.androidtest.di

import dagger.Component
import dagger.android.AndroidInjectionModule


@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class

])
interface ApplicationComponent