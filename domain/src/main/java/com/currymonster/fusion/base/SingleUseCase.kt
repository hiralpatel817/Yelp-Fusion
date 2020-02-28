package com.currymonster.fusion.base

import io.reactivex.Single

abstract class SingleUseCase<Input, Output> {
    protected abstract fun createSingle(data: Input): Single<Output>

    fun execute(withData: Input, transformer: TransformerProvider = Noop): Single<Output> {
        return createSingle(withData).compose(transformer.provideSingleTransformer())
    }
}

fun <Output> SingleUseCase<Unit, Output>.execute(transformer: TransformerProvider = Noop) =
    this.execute(withData = Unit, transformer = transformer)
