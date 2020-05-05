package com.currymonster.fusion.presentation.fragment.businesslist

import android.os.Parcelable
import com.currymonster.fusion.common.ProgressState
import com.currymonster.fusion.common.Reducer
import com.currymonster.fusion.data.Business
import kotlinx.android.parcel.Parcelize

/**
 *  Reducer Actions
 */
sealed class Action {
    data class UpdateBusinesses(val total: Int, val businesses: List<Business>) : Action()
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
    val businesses: List<Business> = emptyList()
) : Parcelable {
    fun next(action: Action): HomeState {
        return copy(
            progressState = ProgressStateReducer.reduce(progressState, action)
        ).let {
            when (action) {
                is Action.UpdateBusinesses ->
                    copy(total = action.total, businesses = businesses.plus(action.businesses))
                else ->
                    it
            }
        }
    }
}