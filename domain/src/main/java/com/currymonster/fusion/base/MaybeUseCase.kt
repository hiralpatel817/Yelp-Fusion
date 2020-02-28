package com.currymonster.fusion.base

import io.reactivex.Maybe

abstract class MaybeUseCase<Input, Output> {
    protected abstract fun createMaybe(data: Input): Maybe<Output>

    fun execute(withData: Input, transformer: TransformerProvider = Noop): Maybe<Output> {
        return createMaybe(withData).compose(transformer.provideMaybeTransformer())
    }
}

fun <Output> MaybeUseCase<Unit, Output>.execute(transformer: TransformerProvider = Noop) =
    this.execute(withData = Unit, transformer = transformer)
