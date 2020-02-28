package com.currymonster.fusion.presentation.items

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.currymonster.fusion.R
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.data.Review
import com.currymonster.fusion.extensions.setThrottledOnClickListener
import com.currymonster.fusion.presentation.fragment.home.HomeViewModel
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_business.view.*

class BusinessItem(
    private val viewModel: HomeViewModel,
    private val business: Business
) : Item<ViewHolder>(business.id.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.item_business

    override fun bind(viewHolder: com.xwray.groupie.ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tvBusinessName.text = business.name

            Glide.with(this)
                .load(business.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCoverPhoto)

            Glide.with(this)
                .load(getRatingDrawable(business.rating))
                .into(ivRating)

            fabYelpCta.setThrottledOnClickListener {
                viewModel.openYelp(business)
            }

            loadReview(this)
        }
    }

    private fun getRatingDrawable(rating: Double) =
        when (rating) {
            0.0, 0.5 ->
                R.drawable.stars_0
            1.0 ->
                R.drawable.stars_1
            1.5 ->
                R.drawable.stars_1_5
            2.0 ->
                R.drawable.stars_2
            2.5 ->
                R.drawable.stars_2_5
            3.0 ->
                R.drawable.stars_3
            3.5 ->
                R.drawable.stars_3_5
            4.0 ->
                R.drawable.stars_4
            4.5 ->
                R.drawable.stars_4_5
            5.0 ->
                R.drawable.stars_5
            else ->
                R.drawable.stars_0
        }

    private fun setLoadingState(view: View) {
        view.viewDivider.visibility = View.GONE
        view.pbSpinner.visibility = View.VISIBLE
        view.ivUserPhoto.visibility = View.GONE
        view.tvUserName.visibility = View.GONE
        view.tvUserReview.visibility = View.GONE
    }

    private fun setErrorState(view: View) {
        view.viewDivider.visibility = View.GONE
        view.pbSpinner.visibility = View.GONE
        view.ivUserPhoto.visibility = View.GONE
        view.tvUserName.visibility = View.GONE
        view.tvUserReview.visibility = View.GONE
    }

    private fun setReadyState(view: View) {
        view.viewDivider.visibility = View.VISIBLE
        view.pbSpinner.visibility = View.GONE
        view.ivUserPhoto.visibility = View.VISIBLE
        view.tvUserName.visibility = View.VISIBLE
        view.tvUserReview.visibility = View.VISIBLE
    }

    private fun loadReview(view: View) {
        val review = viewModel.getReviewForBusiness(business)

        if (null == review) {
            setLoadingState(view)
            viewModel.getReviewFromServer(
                business = business,
                onLoaded = {
                    setReadyState(view)
                    setReview(view, it)
                },
                onError = {
                    setErrorState(view)
                }
            )
        } else {
            setReadyState(view)
            setReview(view, review)
        }
    }

    private fun setReview(view: View, review: Review) {
        view.tvUserName.text = review.user.name
        view.tvUserReview.text = review.text
        Glide.with(view)
            .load(review.user.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view.ivUserPhoto)
    }
}