package com.personal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseActivity<VM : ViewModel, DB : ViewBinding> : AppCompatActivity() {

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        mViewModel = createViewModel()
        mBinding = createDataBinding()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val navigationBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            v.setPadding(systemBars.left, 0, systemBars.right, navigationBars.bottom)
            insets
        }
        initViews()
        initData()
    }

    open fun initViews() {}
    open fun initData() {}


    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): VM {
        val vmClass = (javaClass.getGenericSuperclass() as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return ViewModelProvider(this).get(vmClass)
    }

    @Suppress("UNCHECKED_CAST")
    private fun createDataBinding(): DB {
        val typeList = getTypeList()
        if (typeList.isEmpty() || typeList.size < 2) {
            throw IllegalArgumentException()
        }
        val clazz = typeList[1] as Class<DB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as DB
    }


    private fun getTypeList(): Array<out Type> {
        val type = javaClass.getGenericSuperclass()
        return (type as ParameterizedType).actualTypeArguments
    }


}
