package com.currymonster.fusion.presentation.fragment.businesslist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Transformations
import com.currymonster.fusion.R
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.extensions.distinct
import com.currymonster.fusion.presentation.base.BaseMutableLiveData
import com.currymonster.fusion.presentation.base.BaseViewModel
import com.currymonster.fusion.presentation.common.Dialogs
import com.currymonster.fusion.common.Async
import com.currymonster.fusion.usecase.SearchUseCase
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class BusinessListViewModel @Inject constructor(
    val context: Context,
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    private val _state = BaseMutableLiveData(HomeState())
    val progressState = Transformations.map(_state) { state -> state.progressState }.distinct()
    val totalState = Transformations.map(_state) { state -> state.total }.distinct()
    val businessesState = Transformations.map(_state) { state -> state.businesses }.distinct()

    init {
        fetchBusinesses()
    }

    fun openYelp(business: Business) {
        // Create intent to open browser
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(business.url)
        }.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        // Start intent
        startActivity(
            context, intent, null
        )
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
            .doOnSubscribe {
                // Update state to show progress
                _state.update { s -> s.next(Action.SetLoading(context.getString(R.string.fetching_data))) }
            }
            .doOnSuccess {
                // Update state to remove progress
                _state.update { s -> s.next(Action.ClearLoading) }
            }
            .subscribeBy(
                onSuccess = {
                    // Update state with data coming back from API
                    _state.update { s -> s.next(Action.UpdateBusinesses(it.total, it.businesses)) }
                },
                onError = {
                    // Display error dialog
                    dialogEvent.value =
                        Dialogs.getGenericNetworkDialog(
                            context = context,
                            ctaPositive = {
                                // noop
                            }
                        )
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