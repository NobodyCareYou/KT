package com.personal.eternity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.personal.base.BaseActivity
import com.personal.eternity.databinding.ActivityMainBinding
import com.personal.eternity.helper.EnemyHelper
import com.personal.eternity.view.PrinterTextView
import com.personal.utils.ExcelUtils
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Sheet
import kotlin.math.max


const val ng = "易筋经,九阳真经,九阴真经,葵花宝典,玉女心经,乾坤大挪移"
const val jf = "孤独九剑,六脉神剑,辟邪剑法,玉女剑法,全真剑法,华山剑法 ,雪山剑法,连城剑法,伏魔剑,冲灵剑法,无量剑法,万花剑法,淑女剑法,夺命连环三仙剑"
const val bf = "打狗棒法,太祖棒,齐眉棒"
const val zf = "降龙十八掌,黯然销魂掌,玄冥神掌,寒冰玄掌,般若掌,降魔章,大金刚掌,黑沙掌,庖丁解牛掌,铁砂掌,霹雳掌"
const val qf = "太极拳,罗汉拳,醉拳,少林长拳,武当长拳,七伤拳,霹雳拳,僵尸拳,太祖长拳,苦恼拳,百花错拳,鲁智深醉跌"
const val zhua = "九阴白骨爪,虎爪手,龙爪手,鹰爪功,大擒拿手,小擒拿手,大力金刚爪,兰花佛穴手,分筋错骨手"
const val zhi = "一阳指,拈花指,弹指神功,一指弹,二指弹,金刚指,幻阴指"
const val tf = "如影随形腿,连环迷踪腿,扫叶腿法"
const val qg = "凌波微步,神行百变,梯云纵,逍遥游"
const val aq = "枣核钉,弹指神通,袖里乾坤,满天飞雨"
const val split_char = ","


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val map = mapOf(
        "内功" to ng.split(split_char).toList(),
        "剑法" to jf.split(split_char).toList(),
        "棒法" to bf.split(split_char).toList(),
        "掌法" to zf.split(split_char).toList(),
        "拳法" to qf.split(split_char).toList(),
        "爪法" to zhua.split(split_char).toList(),
        "指法" to zhi.split(split_char).toList(),
        "腿法" to tf.split(split_char).toList(),
        "轻功" to qg.split(split_char).toList(),
        "暗器" to aq.split(split_char).toList(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            val startTime = System.nanoTime();
//            withContext(Dispatchers.Main) {
//                Log.i(TAG, "startTime: ${(System.nanoTime() - startTime) / 1000000}ms")
//            }
//        }

    }

    override fun initViews() {
        lifecycleScope.launch {
            ExcelUtils.writeExcel("sheet1", "$cacheDir/export.xlsx", ::execute)
        }
//        val s = "需要打印效果的文字需要打印效果的文字需"
//        val createPrintTextView = createPrintTextView(s)
//        mBinding.ll.addView(createPrintTextView)
//        createPrintTextView.startPrint()
//        mBinding.fitv.setPrintText(s, 50, "|") {
//
//        }
//        mBinding.fitv.startPrint();

        EnemyHelper.generatePerson().forEach {
            Log.e("zz", "$it")
        }

    }


    var count = 0;
    private fun aaa() {
        if (count == 2) return
        val createPrintTextView = createPrintTextView("ssss")
        mBinding.ll.addView(createPrintTextView, mBinding.ll.childCount)
        createPrintTextView.startPrint()
        count++
    }


    private fun createPrintTextView(text: String): PrinterTextView {
        val printerTextView = PrinterTextView(this)
        printerTextView.setPrintText(text, 100, "_")
        return printerTextView;
    }


    private fun execute(sheet: Sheet) {
        var maxRow = 0;
        map.values.forEach {
            maxRow = max(it.size, maxRow)
        }
        val labelRow = sheet.createRow(0)
        map.keys.forEachIndexed { index, value ->
            labelRow.createCell(index).setCellValue(value);
        }
        var index = 0;
        for (i in 0 until maxRow) {
            val row = sheet.createRow(i + 1)
            map.values.forEach {
                if (i < it.size - 1) {
                    val cell = row.createCell(index)
                    cell.setCellValue(it[i])
                }
                index++
            }
            index = 0
        }
    }
}