package com.currymonster.fusion.net

import com.currymonster.fusion.dto.ReviewResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewService {

    @GET("businesses/{id}/reviews")
    fun getReviewsForBusiness(
        @Path("id") id: String
    ): Single<ReviewResponseDto>
}