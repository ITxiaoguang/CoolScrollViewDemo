package com.xiaoguang.widget.easyscrollview

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.xiaoguang.widget.easyscrollview.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var DP24 = 0
    private var DP58 = 0
    private var SIZE18 = 18.toFloat() / 28

    private var statusBarHeight = 0
    private var scrollHeight = 0
    private var textMaxWidth = 0
    private var textMAxHeight = 0
    private var textChangeX = 0
    private var textChangeY = 0
    private var deviceItemSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImmersionBar.with(this).init()

        initSize()
        initTitleBar()
        initListener()
    }

    private fun initTitleBar() {
        val params: ViewGroup.LayoutParams = v_title_bar.layoutParams
        params.height = statusBarHeight + dp2px(this, 44)
        v_title_bar.layoutParams = params
        rl_title_top.y = statusBarHeight.toFloat()
        // 文字距离左侧 = 24dp
        tv_home.x = DP24.toFloat()
        // 文字距离顶部 = 58dp + statusBarHeight
        tv_home.y = (DP58 + statusBarHeight).toFloat()
    }

    private fun initSize() {
        statusBarHeight = ImmersionBar.getStatusBarHeight(this)
        val textMaxSize: IntArray = measureView(tv_home)
        // 得到文字宽度
        textMaxWidth = textMaxSize[0]
        // 得到文字高度
        textMAxHeight = textMaxSize[1]
        // 文字距离左侧 = 24dp
        DP24 = dp2px(this, 24)
        // 文字距离顶部 = 58dp + statusBarHeight
        DP58 = dp2px(this, 58)

        // 变化的X轴 = 屏幕宽度 / 2 - 文字宽度 / 2 - marginStart24
        textChangeX =
            getScreenWidth(this) / 2 - textMaxWidth / 2 - DP24
        // 变化的Y轴 = 高度58 - (toolbar高度22 - 文字高度) / 2
        textChangeY = DP58 - (dp2px(this, 44) - textMAxHeight) / 2
        // 每个item宽高 = (屏幕宽度 - margin8 * 3) / 2
        deviceItemSize = (getScreenWidth(this) - dp2px(this, 8 * 3)) / 2
        // 滑动变化颜色的距离 = 图片高度276 - CardView穿透上去的83 - toolbar高度44
        scrollHeight = dp2px(this, 276 - 83 - 44) - statusBarHeight
    }

    private fun initListener() {
        nsv_home.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            when {
                // 在顶部
                scrollY <= 0 -> {
                    // 标题栏全透明
                    v_title_bar.alpha = 0f
                    // 图片蒙版全透明
                    iv_home_bg_black.alpha = 0f
                    // 文字大小(28sp)还原
                    tv_home.scaleX = 1f
                    tv_home.scaleY = 1f
                    // 文字x,y轴便宜
                    tv_home.x = DP24.toFloat()
                    tv_home.y = (DP58 + statusBarHeight).toFloat()
                }
                // 在scrollHeight范围内
                scrollY < scrollHeight -> {
                    // 标题栏全透明
                    v_title_bar.alpha = 0f
                    // 滑动百分比
                    val scale: Float = scrollY.toFloat() / scrollHeight
                    // 图片蒙版透明度跟随滑动变化
                    iv_home_bg_black.alpha = scale
                    // 文字大小跟随滑动变化 原始大小(28sp)到18sp之间变化
                    tv_home.scaleX = 1 - scale * (1 - SIZE18)
                    tv_home.scaleY = 1 - scale * (1 - SIZE18)
                    val changeX = (scale * textChangeX).toInt()
                    val changeY = (scale * textChangeY).toInt()
                    // 文字x,y轴偏移，原始位置到居中之间的变化
                    tv_home.x = (DP24 + changeX).toFloat()
                    tv_home.y = (DP58 + statusBarHeight - changeY).toFloat()
                }
                // 在划出scrollHeight范围
                else -> {
                    // 标题栏全黑
                    v_title_bar.alpha = 1.0f
                    // 图片蒙版全黑
                    iv_home_bg_black.alpha = 1.0f
                    // 文字大小改成18sp
                    tv_home.scaleX = SIZE18
                    tv_home.scaleY = SIZE18
                    // 文字x,y轴偏移，即居中显示
                    tv_home.x = (DP24 + textChangeX).toFloat()
                    tv_home.y = (DP58 + statusBarHeight - textChangeY).toFloat()
                }
            }
        }
    }

    // util
    /**
     * dp2px
     */
    private fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.applicationContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * Measure the view.
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    private fun measureView(view: View): IntArray {
        var lp = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
        val lpHeight = lp.height
        val heightSpec: Int = if (lpHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        view.measure(widthSpec, heightSpec)
        return intArrayOf(view.measuredWidth, view.measuredHeight)
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(WINDOW_SERVICE) as WindowManager
            ?: return -1
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }

}