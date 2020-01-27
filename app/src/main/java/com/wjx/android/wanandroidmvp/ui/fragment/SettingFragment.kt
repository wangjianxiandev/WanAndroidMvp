package com.wjx.android.wanandroidmvp.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.blankj.utilcode.util.ColorUtils.getColor
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.wjx.android.wanandroidmvp.Custom.IconPreference
import com.wjx.android.wanandroidmvp.R
import com.wjx.android.wanandroidmvp.base.utils.ColorUtil
import com.wjx.android.wanandroidmvp.base.utils.Constant
import com.wjx.android.wanandroidmvp.base.utils.DataCleanManager
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils
import com.wjx.android.wanandroidmvp.bean.base.Event
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity
import com.wjx.android.wanandroidmvp.ui.activity.SettingActivity
import org.greenrobot.eventbus.EventBus


/**
 * Created with Android Studio.
 * Description: 设置界面
 *
 * @author: Wangjianxian
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
        val nightMode: Boolean = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean(Constant.KEY_NIGHT_MODE, false)
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(parentActivity.packageName, 0).versionName
        findPreference<Preference>("version")?.summary = version
        findPreference<Preference>("clearCache")?.summary = DataCleanManager.getTotalCacheSize(parentActivity)
        findPreference<Preference>("exit")?.isVisible = LoginUtils.isLogin()
        findPreference<SwitchPreference>("night")?.isChecked = nightMode
        findPreference<Preference>("color")?.isEnabled = !nightMode


        // 绑定夜间模式响应事件
        findPreference<SwitchPreference>("night")?.setOnPreferenceChangeListener { preference, newValue ->

            val boolValue = newValue as Boolean
            findPreference<SwitchPreference>("night")?.isChecked = !boolValue
            SPUtils.getInstance(Constant.CONFIG_SETTINGS).put(Constant.KEY_NIGHT_MODE, boolValue)

            // 切换主题
            val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            parentActivity.getDelegate().setLocalNightMode(
                    if (currentMode == Configuration.UI_MODE_NIGHT_NO)
                        AppCompatDelegate.MODE_NIGHT_YES
                    else
                        AppCompatDelegate.MODE_NIGHT_NO)
            startActivity(Intent(parentActivity, SettingActivity::class.java))
            parentActivity.overridePendingTransition(R.anim.animo_alph_open, R.anim.animo_alph_close)
            parentActivity.finish()

            val nightMode: Boolean = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean(Constant.KEY_NIGHT_MODE, false)
            AppCompatDelegate.setDefaultNightMode(
                    if (nightMode)
                        AppCompatDelegate.MODE_NIGHT_YES
                    else
                        AppCompatDelegate.MODE_NIGHT_NO)

            if (nightMode) {
                Constant.setColor(parentActivity, getColor(R.color.colorGray666))
            } else {
                Constant.setColor(parentActivity, getColor(R.color.colorPrimary))
            }

            //通知其他界面立马修改配置
            refreshColor()

            // 通知 MainActivity recreate
            val recreateEvent = Event()
            recreateEvent.target = Event.TARGET_MAIN
            recreateEvent.type = Event.TYPE_CHANGE_DAY_NIGHT_MODE
            EventBus.getDefault().post(recreateEvent)
            true
        }

        // 绑定退出登录响应事件
        findPreference<Preference>("exit")?.onPreferenceClickListener = Preference.OnPreferenceClickListener { preference: Preference? ->
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

                    val shareEvent = Event()
                    shareEvent.target = Event.TARGET_SQUARE_SHARE
                    shareEvent.type = Event.TYPE_LOGOUT
                    EventBus.getDefault().post(shareEvent)

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

        // 绑定清理缓存响应事件
        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定清除缓存吗？")
                positiveButton(text = "清除") {
                    DataCleanManager.clearAllCache(parentActivity)
                    ToastUtils.showShort(R.string.clear_success)
                    findPreference<Preference>("clearCache")?.summary = DataCleanManager.getTotalCacheSize(parentActivity)
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        // 绑定选择颜色响应事件
        findPreference<Preference>("color")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.choose_theme_color)
                colorChooser(ColorUtil.ACCENT_COLORS, initialSelection = Constant.getColor(parentActivity), subColors = ColorUtil.PRIMARY_COLORS_SUB)
                { dialog, color ->
                    Constant.setColor(parentActivity, color)
                    //通知其他界面立马修改配置
                    refreshColor()
                }
                positiveButton(R.string.done)
                negativeButton(R.string.cancel)
            }
            false
        }
    }
    private fun refreshColor() {
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

        val shareEvent = Event()
        shareEvent.target = Event.TARGET_SQUARE_SHARE
        shareEvent.type = Event.TYPE_REFRESH_COLOR
        EventBus.getDefault().post(shareEvent)

        val searchEvent = Event()
        searchEvent.target = Event.TARGET_SEARCH
        searchEvent.type = Event.TYPE_REFRESH_COLOR
        EventBus.getDefault().post(searchEvent)

        val searchResultEvent  = Event()
        searchResultEvent.target = Event.TARGET_SEARCH_RESULT
        searchResultEvent.type = Event.TYPE_REFRESH_COLOR
        EventBus.getDefault().post(searchResultEvent)
    }
}