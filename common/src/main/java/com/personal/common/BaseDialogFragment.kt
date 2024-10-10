package com.personal.common

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import androidx.window.layout.WindowMetricsCalculator
import com.personal.utils.ScreenSizeCompat

/**
 *@author LeeHao
 */
open class BaseDialogFragment<VM : ViewModel, DB : ViewBinding> : DialogFragment() {

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: DB

    private var mCanceledOnTouchOutside = true
    private var mCancelable = true
    private var mGravity = Gravity.CENTER
    private var width = ViewGroup.LayoutParams.MATCH_PARENT
    private var height = ViewGroup.LayoutParams.MATCH_PARENT
    private var widthRatio: Float? = null
    private var heightRatio: Float? = null

    private var offsetX = 0
    private var offsetY = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        savedInstanceState?.apply {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {}
    }

    override fun onStart() {
        super.onStart()
        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())
        val currentBounds = windowMetrics.bounds
        val width = currentBounds.width()
        val height = currentBounds.height()


        dialog?.apply {
            setCanceledOnTouchOutside(mCanceledOnTouchOutside)
            setCancelable(mCancelable)
            window?.apply {
                val screenSize = ScreenSizeCompat.getScreenSize(requireContext())
                attributes.width = widthRatio?.let { screenSize.width * widthRatio as Int } ?: width
                attributes.height = heightRatio?.let { screenSize.height * heightRatio as Int } ?: height
                attributes.gravity = mGravity
                attributes.x = offsetX
                attributes.y = offsetY
            }
        }
    }

    /**
     * 是否允许点击外部关闭
     * @param canceledOnTouchOutside true 允许外部点击关闭 false 不允许外部点击关闭
     */
    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): BaseDialogFragment<VM, DB> {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside
        return this;
    }

    /**
     * 是够允许返回键关闭
     *@param cancelable true 允许返回键关闭  false不允许返回键关闭
     */
    fun setCancelBack(cancelable: Boolean): BaseDialogFragment<VM, DB> {
        this.mCancelable = cancelable
        return this;
    }

    /**
     * 设置Dialog的宽度
     * @param width 宽度
     */
    fun setWidth(width: Int): BaseDialogFragment<VM, DB> {
        this.width = width;
        return this
    }

    /**
     * 设置Dialog的宽度
     * @param height 高度
     */
    fun setHeight(height: Int): BaseDialogFragment<VM, DB> {
        this.height = height;
        return this
    }

    /**
     * 设置Dialog的宽度
     * @param widthRatio 基于屏幕的宽的比例
     */
    fun setWidthRatio(widthRatio: Float): BaseDialogFragment<VM, DB> {
        this.widthRatio = widthRatio;
        return this
    }


    /**
     * 设置Dialog的高度
     * @param heightRatio 基于屏幕的高的比例
     */
    fun setHeightRatio(heightRatio: Float): BaseDialogFragment<VM, DB> {
        this.heightRatio = heightRatio;
        return this
    }

    /**
     * @since 1.0
     * @param gravity
     * @see Gravity.LEFT
     * @see Gravity.TOP
     * @see Gravity.RIGHT
     * @see Gravity.BOTTOM
     * @see Gravity.START
     * @see Gravity.END
     * @return BaseDialogFragment
     **/
    fun setGravity(gravity: Int): BaseDialogFragment<VM, DB> {
        this.mGravity = gravity;
        return this;
    }

}