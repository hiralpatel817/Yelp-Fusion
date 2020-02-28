package com.currymonster.fusion.presentation.fragment.home

import android.os.Parcelable
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.common.Reducer
import com.currymonster.fusion.data.Business
import com.currymonster.fusion.data.Review
import kotlinx.android.parcel.Parcelize

sealed class Event {
}

sealed class Action {
    data class SetTotal(val total: Int) : Action()
    data class UpdateBusinesses(val businesses: List<Business>) : Action()
    data class AddReview(val id: String, val review: Review) : Action()
}

object ProgressStateReducer : Reducer<ProgressState> {
    override fun reduce(state: ProgressState, action: Any): ProgressState {
        return when (action) {
            else ->
                state
        }
    }
}

@Parcelize
data class HomeState(
    val progressState: ProgressState = ProgressState(),
    val total: Int = 0,
    val businesses: List<Business> = emptyList(),
    val reviewsMap: Map<String, Review> = HashMap()
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
                else ->
                    it
            }
        }
    }
}