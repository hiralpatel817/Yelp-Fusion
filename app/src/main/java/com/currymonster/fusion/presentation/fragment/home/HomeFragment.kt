package com.currymonster.fusion.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.currymonster.fusion.R
import com.currymonster.fusion.extensions.initializeWithLinearLayout
import com.currymonster.fusion.presentation.base.BaseFragment
import com.currymonster.fusion.presentation.base.BaseObserver
import com.currymonster.fusion.presentation.common.PaginationListener
import com.currymonster.fusion.presentation.items.BusinessItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels { factory }

    private lateinit var groupAdapter: GroupAdapter<ViewHolder>
    private lateinit var businessesSection: Section

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

        // Set up Recycler View using Groupie
        groupAdapter = GroupAdapter()
        businessesSection = Section()
        groupAdapter.add(businessesSection)

        // Initialize Layout Manager
        rvBusinesses.initializeWithLinearLayout {
            adapter = groupAdapter

            // Add Pagination capability
            addOnScrollListener(
                PaginationListener(
                    layoutManager = layoutManager as LinearLayoutManager,
                    callbacks = viewModel.paginationCallbacks
                )
            )
        }

        viewModel.progressState.observe(this, BaseObserver { progressState ->
            onProgressStateChanged(progressState)
        })

        viewModel.totalState.observe(this, BaseObserver { total ->
            tvTotal.text = String.format(getString(R.string.business_total), total)
        })

        viewModel.businessesState.observe(this, BaseObserver { businesses ->
            businessesSection.update(businesses.map {
                BusinessItem(viewModel, it)
            })

            rvBusinesses.adapter?.notifyDataSetChanged()
        })
    }
}