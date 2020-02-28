package com.currymonster.fusion.presentation.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationListener(
    var layoutManager: LinearLayoutManager,
    var callbacks: Callbacks
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!callbacks.isLoading() && !callbacks.hasLoadedAllItems()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                callbacks.onLoadMore()
            }
        }
    }

    interface Callbacks {
        fun onLoadMore()
        fun hasLoadedAllItems(): Boolean
        fun isLoading(): Boolean
    }
}