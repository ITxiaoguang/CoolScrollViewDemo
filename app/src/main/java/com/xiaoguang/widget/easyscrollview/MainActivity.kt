package com.xiaoguang.widget.easyscrollview

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
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

//    private var v_title_bar: View? = null
//    private var rl_title_top: View? = null
//    private var ib_home_title_notice: ImageButton? = null
//    private var ib_home_title_add: ImageButton? = null
//    private var tv_home_device: TextView? = null

//    private var nsv_home: NestedScrollView? = null
//    private var iv_home_bg_black: ImageView? = null
//    private var rl_home_add_device: RelativeLayout? = null
//    private var rv_home_device: RecyclerView? = null
//    private var home_banner: XBanner? = null

    private var statusBarHeight = 0
    private var scrollHeight = 0
    private var deviceTextMaxWidth = 0
    private var deviceTextMAxHeight = 0
    private var deviceTextChangeX = 0
    private var deviceTextChangeY = 0
    private var deviceItemSize = 0

//    private var adapter: CoolAdapter? = null
//    private val items: List<BaseMultiItem> = ArrayList<BaseMultiItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImmersionBar.with(this).init()

        initView()
        initSize()
        initTitleBar()
        initAdapter()
        initListener()

    }

    private fun initView() {
//        v_title_bar = findView(R.id.v_title_bar)
//        rl_title_top = findView(R.id.rl_title_top)
//        ib_home_title_notice = findView(R.id.ib_home_title_notice)
//        ib_home_title_add = findView(R.id.ib_home_title_add)
//        tv_home_device = findView(R.id.tv_home_device)
//        //        TextBoldUtils.setTextBold(tv_home_device);
//        nsv_home = findView(R.id.nsv_home)
//        iv_home_bg_black = findView(R.id.iv_home_bg_black)
//        rl_home_add_device = findView(R.id.rl_home_add_device)
//        rv_home_device = findView(R.id.rv_home_device)
//        home_banner = findView(R.id.home_banner)
    }

    private fun initSize() {
        statusBarHeight = ImmersionBar.getStatusBarHeight(this)
        val textMaxSize: IntArray = measureView(tv_home_device)
        deviceTextMaxWidth = textMaxSize[0]
        deviceTextMAxHeight = textMaxSize[1]
        DP24 = dp2px(this, 24)
        DP58 = dp2px(this, 58)

        // 变化的X轴 = 屏幕宽度 / 2 - 文字宽度 / 2 - marginStart24
        deviceTextChangeX =
            getScreenWidth(this) / 2 - deviceTextMaxWidth / 2 - DP24
        // 变化的Y轴 = 高度58 - (toolbar高度22 - 文字高度) / 2
        deviceTextChangeY = DP58 - (dp2px(this, 44) - deviceTextMAxHeight) / 2
        // 每个item宽高 = (屏幕宽度 - margin8 * 3) / 2
        deviceItemSize = (getScreenWidth(this) - dp2px(this, 8 * 3)) / 2
    }

    private fun initTitleBar() {
        val params: ViewGroup.LayoutParams = v_title_bar.getLayoutParams()
        params.height = statusBarHeight + dp2px(this, 44)
        v_title_bar.setLayoutParams(params)
        rl_title_top.setY(statusBarHeight.toFloat())
        tv_home_device.setX(DP24.toFloat())
        tv_home_device.setY((DP58 + statusBarHeight).toFloat())
        // 滑动变化颜色的距离 = 图片高度276 - CardView穿透上去的83 - toolbar高度44
        scrollHeight = dp2px(this, 276 - 83 - 44) - statusBarHeight
    }

    private fun initAdapter() {
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("1", "Laser TV", "Living room", true)
//            )
//        )
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("2", "Baby monitor", "Kid room", true)
//            )
//        )
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("3", "Digital Picture Frame", "Living room", true)
//            )
//        )
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("4", "Smart Electric Bed", "Bedroom", true)
//            )
//        )
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("5", "Air Fryer", "Kitchen room", true)
//            )
//        )
//        items.add(
//            BaseMultiItem(
//                HomeDeviceType.DEVICE,
//                HomeDeviceStatusBean("6", "Ice Makers", "Kitchen room", true)
//            )
//        )
//        rv_home_device.setLayoutManager(GridLayoutManager(getContext(), 2))
//        adapter = HomeDevicesAdapter(items, deviceItemSize)
//        adapter.setOnClickFeedbackListener(object : OnClickDeviceListener() {
//            fun clickDevice(position: Int) {}
//        })
//        rv_home_device.setAdapter(adapter)
    }

    private fun initListener() {
        ib_home_title_notice.setOnClickListener(View.OnClickListener { v: View? ->
//            ToastUtil.show(
//                StringUtils.getString(R.string.coming_soon)
//            )
        })
        ib_home_title_add.setOnClickListener(View.OnClickListener { v: View? ->
//            HomeDeviceListActivity.start(
//                getContext()
//            )
        })
        //        tv_home_add_device.setOnClickListener(v ->
//                HomeDeviceListActivity.start(getContext()));
        rl_home_add_device.setOnClickListener(View.OnClickListener { v: View? ->
//            HomeDeviceListActivity.start(
//                getContext()
//            )
        })
        nsv_home.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY <= 0) {
                Log.e("HomeFragment", "scrollY <= 0  $scrollY")
                v_title_bar.setAlpha(0f)
                iv_home_bg_black.setAlpha(0f)
                tv_home_device.setScaleX(1f)
                tv_home_device.setScaleY(1f)
                tv_home_device.setX(DP24.toFloat())
                tv_home_device.setY((DP58 + statusBarHeight).toFloat())
            } else if (scrollY < scrollHeight) {
                Log.e(
                    "HomeFragment",
                    "scrollY < scrollHeight  $scrollY   $scrollHeight"
                )
                val scale: Float = scrollY.toFloat() / scrollHeight
                v_title_bar.setAlpha(0f)
                iv_home_bg_black.setAlpha(scale)
                tv_home_device.setScaleX(1 - scale * (1 - SIZE18))
                tv_home_device.setScaleY(1 - scale * (1 - SIZE18))
                val changeX = (scale * deviceTextChangeX).toInt()
                val changeY = (scale * deviceTextChangeY).toInt()
                tv_home_device.setX((DP24 + changeX).toFloat())
                tv_home_device.setY((DP58 + statusBarHeight - changeY).toFloat())
            } else {
                Log.e("HomeFragment", "      $scrollY")
                v_title_bar.setAlpha(1.0f)
                iv_home_bg_black.setAlpha(1.0f)
                tv_home_device.scaleX = SIZE18
                tv_home_device.setScaleY(SIZE18)
                tv_home_device.setX((DP24 + deviceTextChangeX).toFloat())
                tv_home_device.setY((DP58 + statusBarHeight - deviceTextChangeY).toFloat())
            }
        })
    }

    // util
    /**
     * dp2px
     */
    fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.applicationContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * Measure the view.
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    fun measureView(view: View): IntArray {
        var lp = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
        val lpHeight = lp.height
        val heightSpec: Int
        heightSpec = if (lpHeight > 0) {
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
    fun getScreenWidth(context: Context): Int {
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