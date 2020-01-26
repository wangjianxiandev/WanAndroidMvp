package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wjx.android.wanandroidmvp.R;

/**
 * @author wangyz
 * @time 2019/5/16 10:16
 * @description CustomEditText
 */
public class CustomEditText extends ConstraintLayout {

    private EditText mEditText;

    private EditText mTips;

    private ImageView mShow;

    private String mHint;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText,
                defStyleAttr, 0);
        mHint = ta.getString(R.styleable.CustomEditText_customHint);
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.custom_edtit_text, this,
                true);
        mEditText = rootView.findViewById(R.id.edit_password);
        mTips = rootView.findViewById(R.id.tips);
        mShow = rootView.findViewById(R.id.show);

        mEditText.setHint(mHint);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mShow.setVisibility(View.VISIBLE);
                } else {
                    mShow.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTips.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mEditText.setText(s.toString());
                } else {
                    mEditText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mShow.setOnClickListener(v -> {
            if (mTips.getVisibility() == View.VISIBLE) {
                hideTips();
            } else {
                showTips();
            }
        });
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public void setText(CharSequence text) {
        mEditText.setText(text);
    }

    private void showTips() {
        mShow.setImageResource(R.drawable.password_show);
        mEditText.setVisibility(View.GONE);
        mTips.setText(mEditText.getText());
        mTips.setVisibility(View.VISIBLE);

    }

    private void hideTips() {
        mShow.setImageResource(R.drawable.password_hide);
        mEditText.setVisibility(View.VISIBLE);
        mTips.setVisibility(View.GONE);

    }
}
