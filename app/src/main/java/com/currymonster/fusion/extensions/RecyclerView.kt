package com.currymonster.fusion.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager


/**
 * Function to initialize a RecyclerView a bit more easily.
 */
inline fun androidx.recyclerview.widget.RecyclerView.initializeWithLinearLayout(
    animateChanges: Boolean = false,
    disableScrolling: Boolean = false,
    body: androidx.recyclerview.widget.RecyclerView.() -> Unit
) {

    layoutManager = CustomLinearLayoutManager(context).apply {
        isScrollEnabled = !disableScrolling
    }

    body()

    // Disable item change animations
    val itemAnimator = itemAnimator as androidx.recyclerview.widget.SimpleItemAnimator
    itemAnimator.supportsChangeAnimations = animateChanges
}

/**
 * Special Layout Manager that is used to allow us to easily embed a non scrolling
 * RecyclerView in view in another RecyclerView. Used for example to group transactions
 * together inside a CardView
 */
class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean =
        isScrollEnabled && super.canScrollVertically()
}