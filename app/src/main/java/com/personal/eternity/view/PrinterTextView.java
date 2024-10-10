package com.personal.eternity.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.personal.eternity.R;

import java.util.Timer;
import java.util.TimerTask;


public class PrinterTextView extends AppCompatTextView {
    /**
     * TAG
     */
    private static final String TAG = "PrinterTextView";
    /**
     * 默认打字字符
     */
    private final String DEFAULT_INTERVAL_CHAR = "_";
    /**
     * 默认打字间隔时间
     */
    private final int DEFAULT_TIME_DELAY = 80;
    /**
     * 计时器
     */
    private Timer mTimer;
    /**
     * 需要打字的文字
     */
    private String mPrintStr;
    /**
     * 间隔时间
     */
    private int intervalTime = DEFAULT_TIME_DELAY;
    /**
     * 间隔时间
     */
    private String intervalChar = DEFAULT_INTERVAL_CHAR;
    /**
     * 打字进度
     */
    private int printProgress = 0;


    public PrinterTextView(Context context) {
        super(context);
    }

    public PrinterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置要打字的文字
     *
     * @param str
     */
    public void setPrintText(String str) {
        setPrintText(str, DEFAULT_TIME_DELAY);
    }

    /**
     * 设置需要打字的文字及打字间隔
     *
     * @param str  打字文字
     * @param time 打字间隔(ms)
     */
    public void setPrintText(String str, int time) {
        setPrintText(str, time, DEFAULT_INTERVAL_CHAR);
    }

    /**
     * 设置需要打字的文字,打字间隔,间隔符号
     *
     * @param str          打字文字
     * @param time         打字间隔(ms)
     * @param intervalChar 间隔符号("_")
     */
    public void setPrintText(String str, int time, String intervalChar) {
        if (TextUtils.isEmpty(str) || 0 == time || TextUtils.isEmpty(intervalChar)) {
            return;
        }
        this.mPrintStr = str;
        this.intervalTime = time;
        this.intervalChar = intervalChar;
    }

    /**
     * 开始打字
     */
    public void startPrint() {
        // 判空处理
        if (TextUtils.isEmpty(mPrintStr)) {
            if (!TextUtils.isEmpty(getText().toString())) {
                this.mPrintStr = getText().toString();
            } else {
                return;
            }
        }
        // 重置相关信息
        setText("");
        stopPrint();
        printProgress = 0;
        mTimer = new Timer();
        mTimer.schedule(new PrinterTimeTask(), intervalTime, intervalTime);
    }

    /**
     * 停止打字
     */
    public void stopPrint() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private boolean isPrint;


    /**
     * 打字计时器任务
     */
    class PrinterTimeTask extends TimerTask {

        @Override
        public void run() {
            post(() -> {
                // 如果未显示完,继续显示
                if (printProgress < mPrintStr.length()) {
                    isPrint = true;
                    // (printProgress & 1) == 1 等价于printProgress%2!=0)
                    printProgress++;
                    setText(mPrintStr.substring(0, printProgress) + ((printProgress & 1) == 1 ? intervalChar : ""));
                    play();
                } else {
                    // 如果完成打字,显示完整文字
                    setText(mPrintStr);
                    stopPrint();
                    isPrint = false;
                }
            });
        }
    }

    private void play() {
        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.click);
        player.start();
    }
}