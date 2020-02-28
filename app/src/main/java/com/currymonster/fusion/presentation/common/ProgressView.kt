package com.currymonster.fusion.presentation.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.currymonster.fusion.R
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.extensions.hideKeyboard
import com.currymonster.fusion.extensions.setVisible
import kotlinx.android.synthetic.main.view_progress.view.*

class ProgressView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val duration = 600L
    private var animator: Animator? = null
    private var beta: Float = 0.0f

    init {
        LayoutInflater.from(context).inflate(R.layout.view_progress, this, true)
    }

    fun setState(progressState: ProgressState = ProgressState()) {
        setBackgroundColor(
            Color.argb(
                (255 * progressState.overlayAlpha).toInt(), Color.red(Color.BLACK), Color.green(
                    Color.BLACK
                ), Color.blue(Color.BLACK)
            )
        )

        titleText.text = progressState.title
        titleText.setVisible(!progressState.title.isBlank())

        messageText.text = progressState.message
        messageText.setVisible(!progressState.message.isBlank())

        ensureVisible(progressState.isVisible)
    }

    private fun fadeTo(from: Float, to: Float) {
        animator?.cancel()
        val duration = (duration * Math.abs(to - from)).toLong()
        animator = ObjectAnimator.ofFloat(this, "beta", from, to)
            .setDuration(duration)
            .apply { start() }
    }

    private fun show() {
        setVisible(true)
        fadeTo(beta, 1.0f)
    }

    private fun hide() {
        setVisible(false)
        fadeTo(beta, 0.0f)
    }

    private fun ensureVisible(visible: Boolean) {
        if (visible) {
            show()
            hideKeyboard()
        } else {
            hide()
        }
    }
}