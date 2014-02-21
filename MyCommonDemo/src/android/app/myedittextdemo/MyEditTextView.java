package android.app.myedittextdemo;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyEditTextView extends LinearLayout {
    private static final String TITLE_DEFAULT = "---";
    private static final int TITLE_LENGTH_DEFAULT = 20;
    private static final int VALUE_MAXLENGTH_DEFAULT = 1;

    private TextView titleText = null;
    private EditText editText = null;
    private int mMaxValueLength = 0;

    private static final LayoutParams LINEARLAYOUT_LAYOUTPARAMS = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

    /**
     * @param context
     * @param title          :标题
     * @param value          :内容
     * @param maxValueLength :设置内容的最大限制长度
     * @param isPassword     :设置内容是否为密码
     */
    public MyEditTextView(Context context, String title, String value,
                          int maxValueLength, boolean isPassword) {
        super(context);
        this.setOrientation(VERTICAL);

        initTitleView(context, title);
        initValueView(context, value, maxValueLength, isPassword);
    }

    /**
     * 初始titleView
     *
     * @param context
     * @param title
     */
    private void initTitleView(Context context, String title) {
        titleText = new TextView(context);
        titleText.setText(produceTitle(title));
        titleText.setTextSize(20);
        titleText.setTextColor(Color.WHITE);

        addView(titleText, LINEARLAYOUT_LAYOUTPARAMS);
    }

    /**
     * 初始化valueView
     *
     * @param context
     * @param value
     * @param maxValueLength
     * @param isPassword
     */
    private void initValueView(Context context, String value,
                               int maxValueLength, boolean isPassword) {
        mMaxValueLength = getMaxValueLength(maxValueLength);
        editText = new EditText(context);

        if (isPassword) {
            editText.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }

        editText.setText(produceValue(value));
        editText.setMaxLines(1);
        editText.setSingleLine();
        // editText.setMaxEms( 5 );

        addView(editText, LINEARLAYOUT_LAYOUTPARAMS);
    }

    /**
     * 根据title产生正确的title
     *
     * @param title
     * @return
     */
    private String produceTitle(String title) {
        if (null == title || "".equals(title)) {
            title = TITLE_DEFAULT;
        } else if (title.length() > TITLE_LENGTH_DEFAULT) {
            title = title.substring(0, TITLE_LENGTH_DEFAULT);
        }
        return title;
    }

    /**
     * 获取maxValueLength
     *
     * @param maxValueLength
     * @return
     */
    private int getMaxValueLength(int maxValueLength) {
        if (maxValueLength < VALUE_MAXLENGTH_DEFAULT && 0 != maxValueLength) {
            maxValueLength = VALUE_MAXLENGTH_DEFAULT;
        }
        return maxValueLength;
    }

    /**
     * 根据value产生正确的value
     *
     * @param value
     * @return
     */
    private String produceValue(String value) {
        if (null == value) {
            value = "";
        } else if (value.length() > mMaxValueLength && 0 != mMaxValueLength) {
            value = value.substring(0, mMaxValueLength);
        }
        return value;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTile(String title) {
        titleText.setText(produceTitle(title));
    }

    /**
     * 设置内容
     *
     * @param value
     */
    public void setValue(String value) {
        editText.setText(produceValue(value));
    }

    /**
     * 设置出错提醒
     */
    public void setError(CharSequence error) {
        editText.setError(error);
    }

    /**
     * 设置键盘类型
     *
     * @param keyListener
     */
    public void setKeyListener(KeyListener keyListener) {
        editText.setKeyListener(keyListener);
    }

    /**
     * 获取title
     *
     * @return
     */
    public String getTitle() {
        return titleText.getText().toString();
    }

    /**
     * 获取value
     *
     * @return
     */
    public String getValue() {
        return editText.getText().toString();
    }

    /**
     * 设置文本框是否可编辑
     *
     * @param isEdit
     */
    public void setEnabled(boolean isEdit) {
        editText.setEnabled(isEdit);
    }

    /**
     * 给文本框添加监听器
     */
    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    public void setFocusable(boolean flag) {
        editText.setFocusable(flag);
        editText.setFocusableInTouchMode(flag);
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    public void setInputFilter(final String nums) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.getEditableText().setFilters(
                new InputFilter[]{new InputFilter() {
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        return (nums.contains(source)) ? source : "";
                    }
                }});

    }
}
