package com.currymonster.fusion.presentation.fragment.businesslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.currymonster.fusion.R
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.extensions.setThrottledOnClickListener
import kotlinx.android.synthetic.main.item_business.view.*

class BusinessAdapter(
    private val viewModel: BusinessListViewModel,
    private var businesses: List<Business>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    fun updateBusinesses(businesses: List<Business>) {
        this.businesses = businesses
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return businesses.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_business, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = businesses[position]

        holder.tvBusinessName.text = business.name

        Glide.with(context)
            .load(business.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivCoverPhoto)

        Glide.with(context)
            .load(getRatingDrawable(business.rating))
            .into(holder.ivRating)

        holder.fabYelpCta.setThrottledOnClickListener {
            viewModel.openYelp(business)
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
                0
        }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvBusinessName = view.tv_business_name
    val ivCoverPhoto = view.iv_cover_photo
    val ivRating = view.iv_rating
    val fabYelpCta = view.fab_yelp_cta
}