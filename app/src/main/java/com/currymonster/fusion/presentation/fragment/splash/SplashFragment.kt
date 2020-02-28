package com.currymonster.fusion.presentation.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.currymonster.fusion.R
import com.currymonster.fusion.presentation.base.BaseFragment
import com.currymonster.fusion.presentation.base.BaseObserver
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressState.observe(this, BaseObserver { progressState ->
            onProgressStateChanged(progressState)
        })

        viewModel.productState.observe(this, BaseObserver { product ->
            tvProductName.text = product
        })

        viewModel.envState.observe(this, BaseObserver { env ->
            tvEnvName.text = env
        })

        viewModel.buildTypeState.observe(this, BaseObserver { buildType ->
            tvBuildType.text = buildType
        })

        viewModel.versionState.observe(this, BaseObserver { version ->
            tvVersionName.text = version
        })
    }
}