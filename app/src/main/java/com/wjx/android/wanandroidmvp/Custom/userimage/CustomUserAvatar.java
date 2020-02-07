package com.wjx.android.wanandroidmvp.Custom.userimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;


import androidx.annotation.Nullable;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import static com.blankj.utilcode.util.ColorUtils.getColor;

/**
 * Created with Android Studio.
 * Description: 含有用户默认昵称的头像
 *
 * @author: Wangjianxian
 * @date: 2020/02/07
 * Time: 15:07
 */
@SuppressLint("AppCompatCustomView")
public class CustomUserAvatar extends ImageView {

    private Paint mPaintText;

    private Paint mPaintBackground;

    private Rect mRect;

    private String mUserName;

    public CustomUserAvatar(Context context) {
        super(context);
        init();
    }

    public CustomUserAvatar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomUserAvatar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制发光效果
        int color = getColor(R.color.always_white_text);
        mPaintBackground.setColor(color);
        mPaintBackground.setStyle(Paint.Style.FILL);
        mPaintBackground.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, (getWidth() - 20) / 2, mPaintBackground);
        // 设置文本大小
        mPaintText.setTextSize(getWidth() / 3);
        // 设置文本颜色跟随应用主题颜色
        mPaintText.setColor(Constant.getColor(getContext()));
        // 设置画笔粗细
        mPaintText.setStrokeWidth(5);
        // 设置阴影半径
        mPaintText.setShadowLayer(5, 5, 5, getColor(R.color.black));
        // 绘制文字的最小矩形
        mPaintText.getTextBounds(mUserName, 0, 1, mRect);
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        // baseLine上面是负值，下面是正值
        // 所以getHeight()/2-fontMetricsInt.descent 将文本的bottom线抬高至控件的1/2处
        // + (fontMetricsInt.bottom - fontMetricsInt.top) / 2：(fontMetricsInt.bottom - fontMetricsInt.top) 文本的辅助线（top+bottom)/2就是文本的中位线（我是这样理解的）恰好在控件中位线处
        int baseLine = getHeight() / 2 - fontMetricsInt.descent + (fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        // 水平居中
        mPaintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mUserName, getWidth() / 2, baseLine, mPaintText);
    }


    /**
     * 判断一个字符是否是中文
     */
    public boolean isChineseChar(char c) {
        // 根据字节码判断
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    public boolean isChineseString(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChineseChar(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置显示的名字
     *
     * @param userName
     */
    public void setUserName(String userName) {
        // 中文名字取后两个
        if (isChineseString(userName)) {
            if (userName.length() > 2) {
                mUserName = userName.substring(userName.length() - 2, userName.length());
            } else {
                mUserName = userName;
            }
        } else {
            // 非中文名字取第一个
            if (userName.length() > 1) {
                mUserName = userName.substring(0, 1);
                mUserName = mUserName.toUpperCase();
            } else {
                mUserName = userName;
                mUserName = mUserName.toUpperCase();
            }
        }
        invalidate();
    }
}
