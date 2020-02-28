package com.currymonster.fusion.presentation.activity.intro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.currymonster.fusion.R
import com.currymonster.fusion.presentation.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    private val viewModel: IntroViewModel by viewModels { factory }

    override val toolbar: Toolbar? get() = tbToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        setSupportActionBar(toolbar)
    }
}