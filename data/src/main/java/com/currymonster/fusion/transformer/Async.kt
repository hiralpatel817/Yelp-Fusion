package com.currymonster.fusion.transformer

import com.currymonster.fusion.base.SchedulerTransformerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Async : SchedulerTransformerProvider(Schedulers.io(), AndroidSchedulers.mainThread())
