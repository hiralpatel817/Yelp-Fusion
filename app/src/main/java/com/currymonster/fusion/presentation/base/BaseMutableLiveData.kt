package com.currymonster.fusion.presentation.base

open class BaseMutableLiveData<T : Any>(initialValue: T) : BaseLiveData<T>(initialValue) {

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun update(f: (T) -> T) {
        super.update(f)
    }

    public override fun post(f: (T) -> T) {
        super.post(f)
    }
}
