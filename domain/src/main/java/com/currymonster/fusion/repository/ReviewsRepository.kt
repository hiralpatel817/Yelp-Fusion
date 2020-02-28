package com.currymonster.fusion.repository

import com.currymonster.fusion.data.ReviewResponse
import io.reactivex.Single

interface ReviewsRepository {

    fun getReviewsForBusiness(
        id: String
    ): Single<ReviewResponse>
}