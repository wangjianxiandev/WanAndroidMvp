package com.wjx.android.wanandroidmvp.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.blankj.utilcode.util.ToastUtils
import com.wjx.android.wanandroidmvp.Custom.IconPreference
import com.wjx.android.wanandroidmvp.R
import com.wjx.android.wanandroidmvp.base.utils.*
import com.wjx.android.wanandroidmvp.bean.base.Event
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity
import com.wjx.android.wanandroidmvp.ui.activity.SettingActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description: 设置界面
 *
 * @author: 王拣贤
 * @date: 2020/01/15
 * Time: 21:59
 */
class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var colorPreview: IconPreference? = null
    private lateinit var parentActivity: SettingActivity

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_fragment)
        parentActivity = activity as SettingActivity
        colorPreview = findPreference("color") as IconPreference?
        init()
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "color") {
            colorPreview?.setView()
        }
    }

    private fun init() {
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(parentActivity.packageName, 0).versionName
        findPreference("version")?.summary = version
        findPreference("clearCache").summary = DataCleanManager.getTotalCacheSize(parentActivity)
        findPreference("exit").isVisible = LoginUtils.isLogin()
        findPreference("exit").onPreferenceClickListener = Preference.OnPreferenceClickListener { preference: Preference? ->
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定退出登录吗？")
                cornerRadius
                positiveButton(text = "退出") {
                    LoginUtils.clearLoginInfo()
                    val event = Event()
                    event.target = Event.TARGET_HOME
                    event.type = Event.TYPE_LOGOUT
                    EventBus.getDefault().post(event)

                    val treeEvent = Event()
                    treeEvent.target = Event.TARGET_TREE
                    treeEvent.type = Event.TYPE_LOGIN
                    EventBus.getDefault().post(treeEvent)

                    val projectEvent = Event()
                    projectEvent.target = Event.TARGET_PROJECT
                    projectEvent.type = Event.TYPE_LOGIN
                    EventBus.getDefault().post(projectEvent)

                    val wxEvent = Event()
                    wxEvent.target = Event.TARGET_WX
                    wxEvent.type = Event.TYPE_LOGIN
                    EventBus.getDefault().post(wxEvent)

                    val meEvent = Event()
                    meEvent.target = Event.TARGET_ME
                    meEvent.type = Event.TYPE_LOGOUT
                    EventBus.getDefault().post(meEvent)
                    startActivity(Intent(parentActivity, LoginActivity::class.java))
                    parentActivity.finish()
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        findPreference("clearCache")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定清除缓存吗？")
                positiveButton(text = "清除") {
                    DataCleanManager.clearAllCache(parentActivity)
                    ToastUtils.showShort(R.string.clear_success)
                    findPreference("clearCache").summary = DataCleanManager.getTotalCacheSize(parentActivity)
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        findPreference("color")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.choose_theme_color)
                colorChooser(ColorUtil.ACCENT_COLORS, initialSelection = Constant.getColor(parentActivity), subColors = ColorUtil.PRIMARY_COLORS_SUB)
                { dialog, color ->
                    Constant.setColor(parentActivity, color)
                    //通知其他界面立马修改配置
                    val event = Event()
                    event.target = Event.TARGET_HOME
                    event.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(event)

                    val mainEvent = Event()
                    mainEvent.target = Event.TARGET_MAIN
                    mainEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(mainEvent)

                    val treeEvent = Event()
                    treeEvent.target = Event.TARGET_TREE
                    treeEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(treeEvent)

                    val projectEvent = Event()
                    projectEvent.target = Event.TARGET_PROJECT
                    projectEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(projectEvent)

                    val wxEvent = Event()
                    wxEvent.target = Event.TARGET_WX
                    wxEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(wxEvent)

                    val meEvent = Event()
                    meEvent.target = Event.TARGET_ME
                    meEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(meEvent)

                    val settingEvent = Event()
                    settingEvent.target = Event.TARGET_SETTING
                    settingEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(settingEvent)

                    val squareEvent = Event()
                    squareEvent.target = Event.TARGET_SQUARE
                    squareEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(squareEvent)

                    val navEvent = Event()
                    navEvent.target = Event.TARGET_NAVI
                    navEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(navEvent)

                    val parentSquareEvent = Event()
                    parentSquareEvent.target = Event.TARGET_PARENT_SQUARE
                    parentSquareEvent.type = Event.TYPE_REFRESH_COLOR
                    EventBus.getDefault().post(parentSquareEvent)
                }
                positiveButton(R.string.done)
                negativeButton(R.string.cancel)
            }
            false
        }
    }

}