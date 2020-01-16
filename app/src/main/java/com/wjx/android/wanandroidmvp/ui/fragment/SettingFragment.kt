package com.wjx.android.wanandroidmvp.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.blankj.utilcode.util.ToastUtils
import com.wjx.android.wanandroidmvp.CustomAnim.IconPreference
import com.wjx.android.wanandroidmvp.R
import com.wjx.android.wanandroidmvp.base.utils.*
import com.wjx.android.wanandroidmvp.ui.activity.SettingActivity

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
                colorChooser(ColorUtil.ACCENT_COLORS, initialSelection = Constant.getColor(parentActivity), subColors = ColorUtil.PRIMARY_COLORS_SUB) { dialog, color ->
                    Constant.setColor(parentActivity, color)
                    //通知其他界面立马修改配置
                }
                positiveButton(R.string.done)
                negativeButton(R.string.cancel)
            }
            false
        }

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
}