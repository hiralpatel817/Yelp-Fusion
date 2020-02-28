package com.currymonster.fusion.repository

import com.currymonster.fusion.data.ReviewResponse
import com.currymonster.fusion.net.ReviewService
import io.reactivex.Single

class ReviewsRepositoryImpl(
    private val service: ReviewService
) : ReviewsRepository {

    override fun getReviewsForBusiness(
        id: String
    ): Single<ReviewResponse> =
        service.getReviewsForBusiness(id).map {
            it.toDomain()
        }
}
