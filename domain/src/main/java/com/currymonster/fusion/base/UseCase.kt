package com.currymonster.fusion.base

import io.reactivex.Observable

abstract class UseCase<Input, Output> {
    protected abstract fun createObservable(data: Input): Observable<Output>

    fun execute(withData: Input, transformer: TransformerProvider = Noop): Observable<Output> {
        return createObservable(withData).compose(transformer.provideObservableTransformer())
    }
}

fun <Output> UseCase<Unit, Output>.execute(transformer: TransformerProvider = Noop) =
    this.execute(withData = Unit, transformer = transformer)

