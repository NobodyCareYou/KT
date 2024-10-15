package com.personal

import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.FloatRange
import com.personal.base.EmptyViewModel
import com.personal.common.BaseDialogFragment
import com.personal.eternity.R
import com.personal.eternity.databinding.DialogTestBinding

class CommonDialog(build: Build) : BaseDialogFragment<EmptyViewModel, DialogTestBinding>() {


    private val title: String?
    private val content: String?


    init {
        title = build.title
        content = build.content
    }


    class Build {
        var title: String? = null
        var content: String? = null
        private var gravity: Int = Gravity.CENTER
        private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
        private var height: Int = ViewGroup.LayoutParams.MATCH_PARENT
        private var widthRatio: Float? = null
        private var heightRatio: Float? = null

        fun setTitle(title: String?): Build {
            this.title = title
            return this
        }

        fun setContent(content: String?): Build {
            this.content = content
            return this
        }

        fun setGravity(gravity: Int): Build {
            this.gravity = gravity
            return this
        }

        fun setWidth(width: Int): Build {
            this.width = width
            return this
        }

        fun setHeight(height: Int): Build {
            this.height = height
            return this
        }

        fun setWidthRatio(@FloatRange(from = 0.0, to = 1.0) widthRatio: Float): Build {
            this.widthRatio = widthRatio;
            return this
        }

        fun setHeightRatio(@FloatRange(from = 0.0, to = 1.0) heightRatio: Float): Build {
            this.heightRatio = heightRatio;
            return this
        }

        fun build(): CommonDialog {
            val commonDialog = CommonDialog(this)
            commonDialog.setGravity(gravity)
            widthRatio?.let { commonDialog.setWidthRatio(it) } ?: commonDialog.setWidth(width)
            heightRatio?.let { commonDialog.setHeightRatio(it) } ?: commonDialog.setHeight(height)
            return commonDialog
        }
    }


//    override fun initView() {
//        super.initView()
//        mBinding.title.text = title
//        mBinding.content.text = content
//
//
//
//        if (mBinding.vsSingleButton.parent != null) {
//            val vsSingle = mBinding.vsSingleButton.inflate()
//            val btnSingle = vsSingle.findViewById<Button>(R.id.btn)
//            btnSingle.text = "Ok"
//            btnSingle.setOnClickListener {
//                dismiss()
//            }
//        }
//
//    }

}
