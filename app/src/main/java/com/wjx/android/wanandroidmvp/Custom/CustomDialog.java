package com.wjx.android.wanandroidmvp.Custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wjx.android.wanandroidmvp.R;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/18
 * Time: 10:06
 */
public class CustomDialog extends Dialog {
    private Button mPositiveButton;
    private Button mNegativeButton;
    private TextView mDialogTitle, mAuthor;
    private EditText mTitle,  mLink;

    public CustomDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.add_collect, null);
        mDialogTitle = view.findViewById(R.id.dialog_title);
        mTitle = view.findViewById(R.id.add_collect_title);
        mAuthor = view.findViewById(R.id.add_collect_username);
        mLink = view.findViewById(R.id.add_collect_url);
        mPositiveButton = view.findViewById(R.id.submit_btn);
        mNegativeButton = view.findViewById(R.id.cancel_btn);
        setContentView(view);
    }

    public void setDialogTitle(String title) {
        mDialogTitle.setText(title);
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }

    public void setAuthor(String userName) {
        mAuthor.setText(userName);
    }

    public String getAuthor() {
        return mAuthor.getText().toString();
    }

    public String getLink() {
        return mLink.getText().toString();
    }


    public void setOnPositiveListener(View.OnClickListener listener) {
        mPositiveButton.setOnClickListener(listener);
    }

    public void setOnNegativeListener(View.OnClickListener listener) {
        mNegativeButton.setOnClickListener(listener);
    }
}
