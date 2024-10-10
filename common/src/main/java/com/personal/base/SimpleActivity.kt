package com.personal.base

import androidx.viewbinding.ViewBinding

class SimpleActivity<DB : ViewBinding> : BaseActivity<EmptyViewModel, DB>()