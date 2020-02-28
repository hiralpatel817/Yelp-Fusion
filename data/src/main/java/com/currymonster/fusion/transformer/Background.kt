package com.currymonster.fusion.transformer

import com.currymonster.fusion.base.SchedulerTransformerProvider
import io.reactivex.schedulers.Schedulers

object Background : SchedulerTransformerProvider(Schedulers.io(), Schedulers.computation())
