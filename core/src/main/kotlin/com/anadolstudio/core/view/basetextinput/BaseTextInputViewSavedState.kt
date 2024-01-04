package com.anadolstudio.core.view.basetextinput

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.anadolstudio.core.view.basetextinput.delegates.support_hint.SupportHintState

class BaseTextInputViewSavedState : View.BaseSavedState {

    private var savedState = Data()

    fun saveData(newState: Data) {
        savedState = newState
    }

    fun restoreData(): Data = savedState

    constructor(parcel: Parcel) : super(parcel) {
        savedState = savedState.copy(
                text = parcel.readString().orEmpty(),
                supportHintText = parcel.readString(),
                supportHintState = getSupportHintState(parcel),
        )
    }

    private fun getSupportHintState(parcel: Parcel) = parcel.readString()
            ?.let(SupportHintState::valueOf)
            ?: SupportHintState.INFORMATION

    constructor(superState: Parcelable) : super(superState)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(savedState.text)
        parcel.writeString(savedState.supportHintText)
        parcel.writeString(savedState.supportHintState.name)
    }

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<BaseTextInputViewSavedState> {

            override fun createFromParcel(parcel: Parcel): BaseTextInputViewSavedState = BaseTextInputViewSavedState(parcel)

            override fun newArray(size: Int): Array<BaseTextInputViewSavedState?> = arrayOfNulls(size)
        }
    }

    data class Data(
            val text: String = "",
            val supportHintText: String? = "",
            val supportHintState: SupportHintState = SupportHintState.INFORMATION
    )
}
