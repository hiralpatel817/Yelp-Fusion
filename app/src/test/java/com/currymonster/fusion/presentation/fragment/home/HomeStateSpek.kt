package com.currymonster.fusion.presentation.fragment.home

import com.currymonster.fusion.ContextStubProvider
import com.currymonster.fusion.Util
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class HomeStateSpek : Spek({

    given("HomeState") {
        val context by memoized { ContextStubProvider.newInstance() }
        val homeState = HomeState()

        on("Updating 'total' count to 50") {
            val state = homeState.next(Action.SetTotal(50))

            it("Total should be 50") {
                assertEquals(50, state.total)
            }
        }

        on("Updating 'businesses' with 2 new businesses") {
            val state = homeState.next(
                Action.UpdateBusinesses(
                    listOf(
                        Util.randomBusinessGenerator(),
                        Util.randomBusinessGenerator()
                    )
                )
            )

            it("Total should be 2") {
                assertEquals(2, state.businesses.size)
            }
        }

        on("Updating 'Api loading' state to true") {
            val state = homeState.next(Action.SetApiLoadingState(true))

            it("Api loading should be true") {
                assertEquals(true, state.loadingInProgress)
            }
        }

        on("Updating 'Api Loading' state to false") {
            val state = homeState.next(Action.SetApiLoadingState(false))

            it("Api loading should be true") {
                assertEquals(false, state.loadingInProgress)
            }
        }

        on("Review is added to map") {
            val review = Util.randomReviewGenerator()
            val state = homeState.next(Action.AddReview(review.id, review))

            it("Map key should be business ID") {
                assertEquals(state.reviewsMap[review.id], review)
            }
        }
    }
})