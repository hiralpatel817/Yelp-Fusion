package com.currymonster.fusion.modules

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        EnvModule::class
    ]
)
interface AppComponent {

    fun sessionComponent(): SessionComponent.Builder

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun environmentModule(module: EnvModule): Builder
        fun build(): AppComponent
    }
}