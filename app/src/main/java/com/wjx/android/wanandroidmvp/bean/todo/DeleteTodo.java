package com.wjx.android.wanandroidmvp.bean.todo;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 21:32
 */
public class DeleteTodo {
    /**
     * data : null
     * errorCode : 0
     * errorMsg :
     */

    private Object data;
    private int errorCode;
    private String errorMsg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
