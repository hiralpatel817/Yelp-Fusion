package com.currymonster.fusion.presentation.fragment.businesslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.currymonster.fusion.R
import com.currymonster.fusion.presentation.base.BaseFragment
import com.currymonster.fusion.presentation.base.BaseObserver
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*

class BusinessListFragment : BaseFragment<BusinessListViewModel>() {

    override val viewModel: BusinessListViewModel by viewModels { factory }

    private lateinit var adapter: BusinessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()

        initializeStreams()
    }

    private fun initializeUI() {
        // Initialize Adapter
        adapter =
            BusinessAdapter(
                viewModel,
                emptyList(),
                context!!
            )

        // Initialize Layout Manager
        rv_businesses.layoutManager = LinearLayoutManager(this.context!!)

        // Link adapter to recycler
        rv_businesses.adapter = adapter
    }

    private fun initializeStreams() {
        // Observe Progress State
        viewModel.progressState.observe(this, BaseObserver { progressState ->
            onProgressStateChanged(progressState)
        })

        // Observe Total Items
        viewModel.totalState.observe(this, BaseObserver { total ->
            tv_total.text = String.format(getString(R.string.business_total), total)
        })

        // Observe Business Data
        viewModel.businessesState.observe(this, BaseObserver { businesses ->
            adapter.updateBusinesses(businesses)
        })
    }
}