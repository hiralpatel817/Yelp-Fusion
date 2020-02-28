package com.currymonster.fusion.usecase

import com.currymonster.fusion.base.SingleUseCase
import com.currymonster.fusion.data.ReviewResponse
import com.currymonster.fusion.repository.ReviewsRepository
import io.reactivex.Single
import javax.inject.Inject

class ReviewsUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : SingleUseCase<ReviewsUseCase.Data, ReviewResponse>() {

    override fun createSingle(data: Data): Single<ReviewResponse> =
        reviewsRepository.getReviewsForBusiness(data.id)

    data class Data(
        val id: String
    )
}
