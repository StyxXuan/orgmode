package com.example.org_mode.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.org_mode.R;
import com.example.org_mode.presenter.fragment.frg_Files;

public class DialogUtil {

    private Context mContext;
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;

    public DialogUtil(Activity activity, Context context){
        mActivity = activity;
        mContext = context;
    }

    /** 定义一个接口，当用户点击按钮时，可在主页面进行逻辑操作 */
    public interface OnItemClickListener{
        /** 取消 */
        void onItemCancelClick();
       /** 确定 */
        void onItemConfirmClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    /** 提示框,自定义标题，内容，按钮 */
    public void dialog(String title,String content, String cancelString, String confirmString){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null);
        TextView titleView = view.findViewById(R.id.vi_title);
        TextView contentVIew = view.findViewById(R.id.vi_content);
        TextView cancelButtonView = view.findViewById(R.id.vi_cancel_button);
        TextView confirmButtonView = view.findViewById(R.id.vi_confirm_button);
        final EditText edit = view.findViewById(R.id.vi_edit);
        titleView.setText(title);
        contentVIew.setText(content);
        cancelButtonView.setText(cancelString);
        confirmButtonView.setText(confirmString);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        /** 设置宽为屏幕的0.8大小 */
        lp.width = (int) (mActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.75);
        dialog.getWindow().setAttributes(lp);
        /** 自定义布局应该在这里添加，要在dialog.show()的后面 */
        dialog.getWindow().setContentView(view);
        cancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemCancelClick();
                dialog.dismiss();
            }
        });
        confirmButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_Files.fileName = edit.getText().toString();
                if(frg_Files.fileName == null){
                    System.out.println("!");
                }
                mOnItemClickListener.onItemConfirmClick();
                dialog.dismiss();
            }
        });
    }
}
