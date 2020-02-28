package com.currymonster.fusion.presentation.activity.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.currymonster.fusion.R
import com.currymonster.fusion.presentation.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_intro.*

class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels { factory }

    override val toolbar: Toolbar? get() = tbToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
    }
}