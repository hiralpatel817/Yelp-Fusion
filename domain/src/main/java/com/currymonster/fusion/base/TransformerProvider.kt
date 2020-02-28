package com.currymonster.fusion.base

import io.reactivex.*

interface TransformerProvider {

    fun <T> provideObservableTransformer(): ObservableTransformer<T, T>

    fun <T> provideSingleTransformer(): SingleTransformer<T, T>

    fun <T> provideMaybeTransformer(): MaybeTransformer<T, T>

    fun provideCompletableTransformer(): CompletableTransformer
}

open class SchedulerTransformerProvider(
    private val subscribeScheduler: Scheduler,
    private val observeScheduler: Scheduler
) : TransformerProvider {

    override fun <T> provideObservableTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
        }
    }

    override fun <T> provideSingleTransformer(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
        }
    }

    override fun <T> provideMaybeTransformer(): MaybeTransformer<T, T> {
        return MaybeTransformer { upstream ->
            upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
        }
    }

    override fun provideCompletableTransformer(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)
        }
    }
}

object Noop : TransformerProvider {
    override fun <T> provideObservableTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream -> upstream }
    }

    override fun <T> provideSingleTransformer(): SingleTransformer<T, T> {
        return SingleTransformer { upstream -> upstream }
    }

    override fun <T> provideMaybeTransformer(): MaybeTransformer<T, T> {
        return MaybeTransformer { upstream -> upstream }
    }

    override fun provideCompletableTransformer(): CompletableTransformer {
        return CompletableTransformer { upstream -> upstream }
    }

}