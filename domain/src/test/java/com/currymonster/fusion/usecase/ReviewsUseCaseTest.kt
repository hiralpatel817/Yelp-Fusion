package com.currymonster.fusion.usecase

import com.currymonster.fusion.Util
import com.currymonster.fusion.data.ReviewResponse
import com.currymonster.fusion.repository.ReviewsRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ReviewsUseCaseTest {

    lateinit var useCase: ReviewsUseCase

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun `setup usecase`() {
        useCase = ReviewsUseCase(
            reviewsRepository = mockReviewRepository
        )
    }

    @Test
    fun `Check use case for proper conversion`() {
        useCase.execute(ReviewsUseCase.Data("sdf")).test().await()

        inOrder(mockReviewRepository) {
            verify(mockReviewRepository).getReviewsForBusiness(
                any()
            )
        }
    }

    private val mockReviewRepository = mock<ReviewsRepository> {
        on {
            getReviewsForBusiness(
                any()
            )
        }.thenReturn(
            Single.just(
                ReviewResponse(
                    reviews = listOf(
                        Util.randomReviewGenerator(),
                        Util.randomReviewGenerator()
                    ),
                    total = 2
                )
            )
        )
    }
}