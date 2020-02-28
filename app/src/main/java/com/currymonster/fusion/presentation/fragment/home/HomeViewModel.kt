package com.currymonster.fusion.presentation.fragment.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Transformations
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.data.Review
import com.currymonster.fusion.extensions.distinct
import com.currymonster.fusion.interceptors.FusionRxJava2CallAdapterFactory
import com.currymonster.fusion.presentation.base.BaseMutableLiveData
import com.currymonster.fusion.presentation.base.BaseViewModel
import com.currymonster.fusion.presentation.common.Dialogs
import com.currymonster.fusion.presentation.common.PaginationListener
import com.currymonster.fusion.transformer.Async
import com.currymonster.fusion.usecase.ReviewsUseCase
import com.currymonster.fusion.usecase.SearchUseCase
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    val context: Context,
    private val searchUseCase: SearchUseCase,
    private val reviewsUseCase: ReviewsUseCase
) : BaseViewModel() {

    private val _state = BaseMutableLiveData(HomeState())
    private var loadingInProgress = false

    val progressState = Transformations.map(_state) { state -> state.progressState }.distinct()
    val totalState = Transformations.map(_state) { state -> state.total }.distinct()
    val businessesState = Transformations.map(_state) { state -> state.businesses }.distinct()

    var paginationCallbacks: PaginationListener.Callbacks = object : PaginationListener.Callbacks {
        override fun onLoadMore() {
            fetchBusinesses()
        }

        override fun isLoading(): Boolean {
            return loadingInProgress
        }

        override fun hasLoadedAllItems(): Boolean {
            return (_state.value.businesses.isNotEmpty() && _state.value.businesses.size == _state.value.total)
        }
    }

    init {
        fetchBusinesses()
    }

    fun openYelp(business: Business) {
        startActivity(
            context,
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(business.url)
            },
            null
        )
    }

    fun getReviewForBusiness(business: Business): Review? {
        return _state.value.reviewsMap[business.id]
    }

    fun getReviewFromServer(
        business: Business,
        onLoaded: (review: Review) -> Unit,
        onError: () -> Unit
    ) {
        reviewsUseCase.execute(
            ReviewsUseCase.Data(
                business.id
            ), Async
        ).subscribeBy(
            onSuccess = {
                if (it.reviews.isNotEmpty()) {
                    _state.update { s ->
                        s.next(Action.AddReview(business.id, it.reviews[0]))
                    }

                    onLoaded.invoke(it.reviews[0])
                } else {
                    onError.invoke()
                }
            },
            onError = {
                onError.invoke()
            }
        ).also {
            autoDispose(it)
        }
    }

    private fun fetchBusinesses() {
        searchUseCase.execute(
            SearchUseCase.Data(
                latitude = IRVINE_LATITUDE,
                longitude = IRVINE_LONGITUDE,
                offset = _state.value.businesses.size,
                limit = PAGE_LIMIT
            ), Async
        )
            .doOnSubscribe { loadingInProgress = true }
            .doOnSuccess { loadingInProgress = false }
            .subscribeBy(
                onSuccess = {
                    _state.update { s -> s.next(Action.SetTotal(it.total)) }
                    _state.update { s -> s.next(Action.UpdateBusinesses(it.businesses)) }
                },
                onError = {
                    when (it) {
                        is HttpException -> {
                            dialogEvent.value =
                                Dialogs.getGenericNetworkRetryDialog(
                                    context = context,
                                    ctaPositive = {
                                        fetchBusinesses()
                                    }
                                )
                        }
                        is FusionRxJava2CallAdapterFactory.ServerException -> {
                            dialogEvent.value =
                                Dialogs.getGenericNetworkRetryDialog(
                                    context = context,
                                    title = it.status,
                                    description = it.description,
                                    ctaPositive = {
                                        fetchBusinesses()
                                    }
                                )
                        }

                        is FusionRxJava2CallAdapterFactory.NetworkException -> {
                            dialogEvent.value =
                                Dialogs.getGenericNetworkRetryDialog(
                                    context = context,
                                    description = it.description,
                                    ctaPositive = {
                                        fetchBusinesses()
                                    }
                                )
                        }
                        else -> {
                            dialogEvent.value =
                                Dialogs.getGenericNetworkDialog(
                                    context = context,
                                    ctaPositive = {}
                                )
                        }

                    }
                }
            ).also {
                autoDispose(it)
            }
    }

    companion object {

        const val IRVINE_LATITUDE = "33.6846"
        const val IRVINE_LONGITUDE = "-117.8265"
        const val PAGE_LIMIT = 10
    }
}