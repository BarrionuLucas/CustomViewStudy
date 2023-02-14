package com.example.customviewstudy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviewstudy.databinding.ProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var title:String? = null
    private var loadingTitle:String? = null

    private val binding = ProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value){
            field = value
            refreshState()
        }
    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let{
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProgressButton)

            setBackgroundResource(R.drawable.progress_button_background)

            val titleResId = attributes.getResourceId(R.styleable.ProgressButton_progress_button_title, 0)
            if(titleResId != 0){
                title = context.getString(titleResId)
            }

            val loadingTitleResId = attributes.getResourceId(R.styleable.ProgressButton_progress_button_loading_title, 0)
            if(loadingTitleResId != 0){
                loadingTitle = context.getString(loadingTitleResId)
            }

            attributes.recycle()
        }
    }

    fun setLoading(isLoading: Boolean){
        if(isLoading){
            state = ProgressButtonState.Loading
        }else{
            state = ProgressButtonState.Normal
        }
    }

    private fun refreshState(){
        isEnabled = state.isEnable
        isClickable = state.isEnable
        refreshDrawableState()

        binding.textTitle.run{
            isEnabled = state.isEnable
            isClickable = state.isEnable
        }

        binding.progressButton.visibility = state.progressVisibility

        when(state){
            ProgressButtonState.Normal -> binding.textTitle.text = title
            ProgressButtonState.Loading -> binding.textTitle.text = loadingTitle
        }
    }

    sealed class ProgressButtonState(val isEnable:Boolean, val progressVisibility: Int){
        object Normal: ProgressButtonState(true, View.GONE)
        object Loading: ProgressButtonState(false, View.VISIBLE)
    }

}