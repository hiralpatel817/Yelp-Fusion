package com.currymonster.fusion.usecase

import com.currymonster.fusion.Util
import com.currymonster.fusion.data.ReviewResponse
import com.currymonster.fusion.data.SearchResponse
import com.currymonster.fusion.repository.ReviewsRepository
import com.currymonster.fusion.repository.SearchRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ReviewsUseCaseTest {

    lateinit var useCase: SearchUseCase

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun `setup usecase`() {
        useCase = SearchUseCase(
            searchRepository = mockSearchRepository
        )
    }

    @Test
    fun `Check use case for proper conversion`() {
        useCase.execute(SearchUseCase.Data("23.2", "23.3", 0, 10)).test().await()

        inOrder(mockSearchRepository) {
            verify(mockSearchRepository).searchBusinesses(
                any(),
                any(),
                any(),
                any()
            )
        }
    }

    private val mockSearchRepository = mock<SearchRepository> {
        on {
            searchBusinesses(
                any(),
                any(),
                any(),
                any()
            )
        }.thenReturn(
            Single.just(
                SearchResponse(
                    businesses = listOf(
                        Util.randomBusinessGenerator(),
                        Util.randomBusinessGenerator()
                    ),
                    total = 2
                )
            )
        )
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