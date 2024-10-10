package com.personal.eternity

import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import com.personal.base.EmptyViewModel
import com.personal.common.BaseDialogFragment
import com.personal.eternity.databinding.DialogTestBinding

class TestDialog : BaseDialogFragment<EmptyViewModel, DialogTestBinding>() {


    class Build(val activity: FragmentActivity) {


        fun build(): TestDialog {
            val dialog = TestDialog();
            dialog.setWidthRatio(0.8f)
                .setGravity(Gravity.CENTER)
                .show(activity.supportFragmentManager, "")
            return dialog;
        }


    }


}
