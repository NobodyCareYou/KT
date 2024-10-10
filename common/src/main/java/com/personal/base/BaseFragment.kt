package com.personal.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type



abstract class BaseFragment<VM : ViewModel, DB : ViewBinding> : Fragment() {
    private var isViewCreated = false
    private var isDataLoaded = false
    private lateinit var rootView: View
    protected lateinit var activity: Activity
    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: DB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isViewCreated = true
        mViewModel = createViewModel()
        mBinding = createDataBinding()
        rootView = mBinding.root
        initViews(rootView)
        return rootView
    }


    override fun onResume() {
        super.onResume()
        //数据懒加载
        if (isViewCreated && !isDataLoaded) {
            //只会在fragment可见时调用一次
            initData()
            isDataLoaded = true
        }
    }

    open fun initViews(view: View) {}

    open fun initData() {}

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[typeList[0] as Class<VM>]
    }

    @Suppress("UNCHECKED_CAST")
    private fun createDataBinding(): DB {
        val clazz = typeList[1] as Class<DB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, getLayoutInflater()) as DB

    }

    private val typeList: Array<Type>
        get() {
            val type = javaClass.getGenericSuperclass()
            return (type as ParameterizedType).actualTypeArguments
        }

}
