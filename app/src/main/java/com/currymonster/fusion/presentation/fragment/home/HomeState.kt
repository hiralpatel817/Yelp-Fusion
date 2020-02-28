package com.currymonster.fusion.presentation.fragment.home

import android.os.Parcelable
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.common.Reducer
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.data.Review
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 *  Single Event Actions
 */
sealed class Event {
}

/**
 *  Reducer Actions
 */
sealed class Action {
    data class SetTotal(val total: Int) : Action()
    data class UpdateBusinesses(val businesses: List<Business>) : Action()
    data class AddReview(val id: String, val review: Review) : Action()
    data class SetApiLoadingState(val isLoading: Boolean) : Action()
    data class SetLoading(val loading: String) : Action()
    object ClearLoading : Action()
}

/**
 *  Progress Reducer (connects to activity to show progress dialogs)
 */
object ProgressStateReducer : Reducer<ProgressState> {
    override fun reduce(state: ProgressState, action: Any): ProgressState {
        return when (action) {
            is Action.SetLoading ->
                state.new()
                    .withTitle(action.loading)
            is Action.ClearLoading ->
                state.clear()
            else ->
                state
        }
    }
}

/**
 *  State holder for home view model
 */
@Parcelize
data class HomeState(
    val progressState: ProgressState = ProgressState(),
    val total: Int = 0,
    val businesses: List<Business> = emptyList(),
    val reviewsMap: Map<String, Review> = HashMap<String, Review>(),
    val loadingInProgress: Boolean = false
) : Parcelable {
    fun next(action: Action): HomeState {
        return copy(
            progressState = ProgressStateReducer.reduce(progressState, action)
        ).let {
            when (action) {
                is Action.SetTotal ->
                    copy(total = action.total)
                is Action.UpdateBusinesses ->
                    copy(businesses = businesses.plus(action.businesses))
                is Action.AddReview -> {
                    copy(reviewsMap = reviewsMap.plus(Pair(action.id, action.review)))
                }
                is Action.SetApiLoadingState -> {
                    copy(loadingInProgress = action.isLoading)
                }
                else ->
                    it
            }
        }
    }

    @IgnoredOnParcel
    val hasAllLoaded = businesses.isNotEmpty() && businesses.size == total
}