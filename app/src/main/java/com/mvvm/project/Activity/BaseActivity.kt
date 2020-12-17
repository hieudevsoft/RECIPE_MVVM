package com.mvvm.project.Activity

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mvvm.project.R
import com.mvvm.project.databinding.AcitivityBaseBinding


abstract class BaseActivity : AppCompatActivity() {
    private lateinit var binding: AcitivityBaseBinding
    public val rootBinding get() = binding
     var mProgressBar:ProgressBar?=null
    override fun setContentView(layoutResID: Int) {
        binding = AcitivityBaseBinding.inflate(layoutInflater)
        val constraintLayout =
            layoutInflater.inflate(R.layout.acitivity_base, null) as ConstraintLayout
        mProgressBar = constraintLayout.findViewById(R.id.progressBarLoading)
        layoutInflater.inflate(layoutResID, binding.layoutContent, true)
        super.setContentView(binding.root)
    }

    fun showProgressBar(visible: Boolean) {
        mProgressBar?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

}
