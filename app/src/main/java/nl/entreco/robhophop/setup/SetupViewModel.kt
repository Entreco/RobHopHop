package nl.entreco.robhophop.setup

import android.view.View
import android.view.animation.BounceInterpolator
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class SetupViewModel @Inject constructor() : ViewModel() {

    val dot1 = ObservableBoolean(false)
    val dot2 = ObservableBoolean(false)
    val dot3 = ObservableBoolean(false)
    val dot4 = ObservableBoolean(false)
    val dot5 = ObservableBoolean(false)
    val showError = ObservableBoolean()

    private val dots = listOf(dot1, dot2, dot3, dot4, dot5)

    private val events = MutableSharedFlow<SetupEvent>()
    fun events(): SharedFlow<SetupEvent> = events

    private val enteredPinCode = ObservableField("").apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                updateDots()
            }
        })
    }

    private fun updateDots() {
        val length = enteredPinCode.get()?.length ?: 0
        (0 until length).forEach { dots[it].set(true) }
        (length until dots.size).forEach { dots[it].set(false) }

        if (length == dots.size) {
            tryLogin()
        }
    }


    fun onEntered(number: Int) {
        enteredPinCode.set(enteredPinCode.get()?.plus(number.toString()))
    }

    fun onBackspace() {
        enteredPinCode.set(enteredPinCode.get()?.dropLast(1))
    }

    private fun tryLogin() {
        viewModelScope.launch {
            if ("06720" == enteredPinCode.get()) {
                events.emit(SetupEvent.Go)
            } else {
                showError.set(true)
                delay(100)
                showError.set(false)

                delay(100)
                enteredPinCode.set("")
            }
        }
    }
}

object SetupBindings {

    @JvmStatic
    @BindingAdapter("pinSelected")
    fun pinSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @JvmStatic
    @BindingAdapter("errorAnimation")
    fun errorAnimation(view: View, hasError: Boolean) {
        val duration: Long = 50
        val translation: Float = 20f
        view.animate()
            .translationX(translation)
            .withEndAction {
                view.animate().translationX(0f).setDuration(duration).start()
            }
            .setDuration(duration)
            .setInterpolator(BounceInterpolator())
            .start()
    }
}