package com.currymonster.fusion.base

import io.reactivex.Completable

abstract class CompletableUseCase<Input> {
    protected abstract fun createCompletable(data: Input): Completable

    fun execute(withData: Input, transformer: TransformerProvider = Noop): Completable {
        return createCompletable(withData).compose(transformer.provideCompletableTransformer())
    }
}

fun CompletableUseCase<Unit>.execute(transformer: TransformerProvider = Noop) =
    this.execute(withData = Unit, transformer = transformer)
