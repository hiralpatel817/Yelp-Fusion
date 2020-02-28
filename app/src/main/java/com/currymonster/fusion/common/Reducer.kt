package com.currymonster.fusion.common

interface Reducer<S> {
    fun reduce(state : S, action : Any) : S
}