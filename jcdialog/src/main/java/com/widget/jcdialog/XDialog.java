package com.widget.jcdialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.widget.jcdialog.bean.BuildBean;
import com.widget.jcdialog.bean.PopuBean;
import com.widget.jcdialog.bean.TieBean;
import com.widget.jcdialog.listener.DialogAssigner;
import com.widget.jcdialog.listener.DialogUIDateTimeSaveListener;
import com.widget.jcdialog.listener.DialogUIItemListener;
import com.widget.jcdialog.listener.DialogUIListener;
import com.widget.jcdialog.listener.TdataListener;
import com.widget.jcdialog.widget.JDAddressSeletor.BottomDialog;
import com.widget.jcdialog.widget.JDAddressSeletor.DefaultAddressProvider;
import com.widget.jcdialog.widget.JDAddressSeletor.OnAddressSelectedListener;
import com.widget.jcdialog.widget.PopupWindowView;
import com.widget.jcdialog.widget.pickerview.OptionsPickerView;
import com.widget.jcdialog.widget.pickerview.TimePickerView;
import com.widget.jcdialog.widget.pswKeyBoard.widget.PopEnterPassword;
import com.widget.jcdialog.widget.updateDialog.UpdateAppBean;
import com.widget.jcdialog.widget.updateDialog.UpdateAppManager;
import com.widget.jcdialog.widget.updateDialog.listener.IUpdateCallback;

import java.util.Date;
import java.util.List;

/**
 * 类名称：DialogUtils
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/26 15:45
 * 描述：Dialog管理工具类
 */
public class XDialog {

    /**
     * 全局上下文
     */
    public static Context appContext;

    /**
     * 如果有使用到showTost...相关的方法使用之前需要初始化该方法
     */
    public static void init(Context appContext) {
        XDialog.appContext = appContext;
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(DialogInterface... dialogs) {
        if (dialogs != null && dialogs.length > 0) {
            for (DialogInterface dialog : dialogs) {
                if (dialog instanceof Dialog) {
                    Dialog dialog1 = (Dialog) dialog;
                    if (dialog1.isShowing()) {
                        dialog1.dismiss();
                    }
                } else if (dialog instanceof AppCompatDialog) {
                    AppCompatDialog dialog2 = (AppCompatDialog) dialog;
                    if (dialog2.isShowing()) {
                        dialog2.dismiss();
                    }
                }
            }

        }
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(BuildBean buildBean) {
        if (buildBean != null) {
            if (buildBean.dialog != null && buildBean.dialog.isShowing()) {
                buildBean.dialog.dismiss();
            }
            if (buildBean.alertDialog != null && buildBean.alertDialog.isShowing()) {
                buildBean.alertDialog.dismiss();
            }
        }
    }

    /**
     * 关闭弹出框
     */
    public static void dismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /***
     * 弹出日期选择器
     *
     * @param context   上下文
     * @param gravity   显示位置
     * @param dateTitle 显示标题
     * @param date      当前选择日期
     * @param dateType  显示日期样式DateSelectorWheelView.TYPE_YYYYMMDD TYPE_YYYYMMDDHHMM TYPE_YYYYMMDDHHMMSS
     * @param tag       view标记tag 一个页面多个日期选择器是可以加标记区分
     * @param listener
     * @return
     */
    public static BuildBean showDatePick(Context context, int gravity, String dateTitle, long date, int dateType, int tag, DialogUIDateTimeSaveListener listener) {
        return DialogAssigner.getInstance().assignDatePick(context, gravity, dateTitle, date, dateType, tag, listener);
    }
    public static TimePickerView showTimePickView(Context context, String dateTitle, TimePickerView.Type dateType, TimePickerView.OnTimeSelectListener listener) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView(context, dateType);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setTitle(dateTitle);
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(listener);
        pvTime.show();
        return pvTime;
    }
    public static OptionsPickerView showAddrPickView(Context context, String addrTitle,DefaultAddressProvider addressProvider,
                                                     OptionsPickerView.OnOptionsSelectListener listener) {
        //选项选择器
        OptionsPickerView pvOptions = new OptionsPickerView(context);
        //三级联动效果
        pvOptions.setPicker(addressProvider.provideProvince(), addressProvider.provideCities(), addressProvider.provideCounties(), true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle(addrTitle);
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(listener);
        pvOptions.setCancelable(true);
        pvOptions.show();
        return pvOptions;
    }

    public static Dialog showAddressPick(Context context,OnAddressSelectedListener listener) {
        BottomDialog dialog = new BottomDialog(context);
        dialog.setOnAddressSelectedListener(listener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static BottomDialog showAddressDialog(Context context) {
        BottomDialog dialog = new BottomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * 弹出toast 默认可取消可点击
     *
     * @param context 上下文
     * @param msg     提示文本
     */
    public static BuildBean showDialogTie(Context context, CharSequence msg) {
        return showDialogTie(context, msg, true, true);
    }

    /**
     * 弹出toast
     *
     * @param context          上下文
     * @param msg              提示文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     */
    public static BuildBean showDialogTie(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable) {
        return DialogAssigner.getInstance().assignAlert(context, "", msg, "", "", "", "", true, cancleable, outsideTouchable, null);
    }

    /**
     * 横向加载框 默认白色背景可取消可点击
     *
     * @param context 上下文
     * @param msg     提示文本
     */
    public static BuildBean showLoadingHorizontal(Context context, CharSequence msg) {
        return showLoadingHorizontal(context, msg, true, true, true);
    }

    /**
     * 横向加载框 默认可取消可点击
     *
     * @param context   上下文
     * @param msg       提示文本
     * @param isWhiteBg true为白色背景false为灰色背景
     */
    public static BuildBean showLoadingHorizontal(Context context, CharSequence msg, boolean isWhiteBg) {
        return showLoadingHorizontal(context, msg, true, true, isWhiteBg);
    }

    /**
     * 横向加载框
     *
     * @param context          上下文
     * @param msg              提示文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param isWhiteBg        true为白色背景false为灰色背景
     */
    public static BuildBean showLoadingHorizontal(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        return DialogAssigner.getInstance().assignLoadingHorizontal(context, msg, cancleable, outsideTouchable, isWhiteBg);
    }

    /**
     * md风格横向加载框 默认白色背景可取消可点击
     *
     * @param context 上下文
     * @param msg     提示文本
     */
    public static BuildBean showMdLoadingHorizontal(Context context, CharSequence msg) {
        return showMdLoadingHorizontal(context, msg, true, true, true);
    }

    /**
     * md风格横向加载框 默认可取消可点击
     *
     * @param context   上下文
     * @param msg       提示文本
     * @param isWhiteBg true为白色背景false为灰色背景
     */
    public static BuildBean showMdLoadingHorizontal(Context context, CharSequence msg, boolean isWhiteBg) {
        return showMdLoadingHorizontal(context, msg, true, true, isWhiteBg);
    }

    /**
     * md风格横向加载框
     *
     * @param context          上下文
     * @param msg              提示文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param isWhiteBg        true为白色背景false为灰色背景
     */
    public static BuildBean showMdLoadingHorizontal(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        return DialogAssigner.getInstance().assignMdLoadingHorizontal(context, msg, cancleable, outsideTouchable, isWhiteBg);
    }

    /**
     * 竖向加载框  默认白色背景可取消可点击
     *
     * @param context 上下文
     * @param msg     提示文本
     */
    public static BuildBean showLoadingVertical(Context context, CharSequence msg) {
        return showLoadingVertical(context, msg, true, true, true);
    }

    /**
     * 竖向加载框 默认可取消可点击
     *
     * @param context   上下文
     * @param msg       提示文本
     * @param isWhiteBg true为白色背景false为灰色背景
     */
    public static BuildBean showLoadingVertical(Context context, CharSequence msg, boolean isWhiteBg) {
        return showLoadingVertical(context, msg, true, true, isWhiteBg);
    }

    /**
     * 竖向加载框
     *
     * @param context          上下文
     * @param msg              提示文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param isWhiteBg        true为白色背景false为灰色背景
     */
    public static BuildBean showLoadingVertical(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        return DialogAssigner.getInstance().assignLoadingVertical(context, msg, cancleable, outsideTouchable, isWhiteBg);
    }

    /**
     * md风格竖向加载框  默认白色背景可取消可点击
     *
     * @param context 上下文
     * @param msg     提示文本
     */
    public static BuildBean showMdLoadingVertical(Context context, CharSequence msg) {
        return showMdLoadingVertical(context, msg, true, true, true);
    }

    /**
     * md风格竖向加载框 默认可取消可点击
     *
     * @param context   上下文
     * @param msg       提示文本
     * @param isWhiteBg true为白色背景false为灰色背景
     */
    public static BuildBean showMdLoadingVertical(Context context, CharSequence msg, boolean isWhiteBg) {
        return showMdLoadingVertical(context, msg, true, true, isWhiteBg);
    }

    /**
     * md风格竖向加载框
     *
     * @param context          上下文
     * @param msg              提示文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param isWhiteBg        true为白色背景false为灰色背景
     */
    public static BuildBean showMdLoadingVertical(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        return DialogAssigner.getInstance().assignMdLoadingVertical(context, msg, cancleable, outsideTouchable, isWhiteBg);
    }

    /***
     * md风格弹出框 默认可取消可点击
     *
     * @param activity 所在activity
     * @param title    标题 不传则无标题
     * @param msg      消息
     * @param listener 事件监听
     * @return
     */
    public static BuildBean showMdAlert(Activity activity, CharSequence title, CharSequence msg, DialogUIListener listener) {
        return showMdAlert(activity, title, msg, true, true, listener);
    }

    /***
     * md风格弹出框
     *
     * @param activity         所在activity
     * @param title            标题 不传则无标题
     * @param msg              消息
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     * @return
     */
    public static BuildBean showMdAlert(Activity activity, CharSequence title, CharSequence msg, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        return DialogAssigner.getInstance().assignMdAlert(activity, title, msg, cancleable, outsideTouchable, listener);
    }

    /**
     * md风格多选框  默认可取消可点击
     *
     * @param activity     所在activity
     * @param title        标题 不传则无标题
     * @param words        消息数组
     * @param checkedItems 默认选中项
     * @param listener     事件监听
     */
    public static BuildBean showMdMultiChoose(Activity activity, CharSequence title, CharSequence[] words, boolean[] checkedItems, DialogUIListener listener) {
        return showMdMultiChoose(activity, title, words, checkedItems, true, true, listener);
    }

    /***
     * md风格多选框
     *
     * @param activity         所在activity
     * @param title            标题 不传则无标题
     * @param words            消息数组
     * @param checkedItems     默认选中项
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     * @return
     */
    public static BuildBean showMdMultiChoose(Activity activity, CharSequence title, CharSequence[] words, boolean[] checkedItems, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        return DialogAssigner.getInstance().assignMdMultiChoose(activity, title, words, checkedItems, cancleable, outsideTouchable, listener);
    }

    /**
     * 单选框  默认可取消可点击
     *
     * @param activity      所在activity
     * @param title         标题 不传则无标题
     * @param defaultChosen 默认选中项
     * @param words         消息数组
     * @param listener      事件监听
     */
    public static BuildBean showSingleChoose(Activity activity, CharSequence title, int defaultChosen, CharSequence[] words, DialogUIItemListener listener) {
        return showSingleChoose(activity, title, defaultChosen, words, true, true, listener);
    }

    /**
     * 单选框
     *
     * @param activity         所在activity
     * @param title            标题 不传则无标题
     * @param defaultChosen    默认选中项
     * @param words            消息数组
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     */
    public static BuildBean showSingleChoose(Activity activity, CharSequence title, int defaultChosen, CharSequence[] words, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        return DialogAssigner.getInstance().assignSingleChoose(activity, title, defaultChosen, words, cancleable, outsideTouchable, listener);
    }

    /**
     * 提示弹出框 默认可取消可点击
     *
     * @param activity 所在activity
     * @param title    标题 不传则无标题
     * @param listener 事件监听
     */
    public static BuildBean showAlert(Activity activity, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                                      CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, DialogUIListener listener) {
        return showAlert(activity, title, msg, hint1, hint2, firstTxt, secondTxt, isVertical, true, true, listener);
    }

    /**
     * 提示弹出框
     *
     * @param activity         所在activity
     * @param title            标题 不传则无标题
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     */
    public static BuildBean showAlert(Activity activity, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                                      CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        return DialogAssigner.getInstance().assignAlert(activity, title, msg, hint1, hint2, firstTxt, secondTxt, isVertical, cancleable, outsideTouchable, listener);
    }

    /**
     * 中间弹出列表 默认可取消可点击
     *
     * @param context  上下文
     * @param datas    素组集合
     * @param listener 事件监听
     * @return
     */
    public static BuildBean showCenterSheet(Context context, List<TieBean> datas, DialogUIItemListener listener) {
        return showCenterSheet(context, datas, true, true, listener);
    }

    /***
     * 中间弹出列表
     *
     * @param context          上下文
     * @param datas            素组集合
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     * @return
     */
    public static BuildBean showCenterSheet(Context context, List<TieBean> datas, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        return DialogAssigner.getInstance().assignCenterSheet(context, datas, cancleable, outsideTouchable, listener);
    }

    /**
     * md风格竖向底部弹出列表 默认可取消可点击
     *
     * @param context   上下文
     * @param title     标题
     * @param datas     集合需要BottomSheetBean对象
     * @param bottomTxt 底部item文本
     * @param listener  事件监听
     * @return
     */
    public static BuildBean showMdBottomSheet(Context context, boolean isVertical, CharSequence title, List<TieBean> datas, CharSequence bottomTxt, int columnsNum, DialogUIItemListener listener) {
        return showMdBottomSheet(context, isVertical, title, datas, bottomTxt, columnsNum, true, true, listener);
    }

    /***
     * md风格弹出列表
     *
     * @param context          上下文
     * @param title            标题
     * @param datas            集合需要BottomSheetBean对象
     * @param bottomTxt        底部item文本
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @param listener         事件监听
     * @return
     */
    public static BuildBean showMdBottomSheet(Context context, boolean isVertical, CharSequence title, List<TieBean> datas, CharSequence bottomTxt, int columnsNum, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        return DialogAssigner.getInstance().assignMdBottomSheet(context, isVertical, title, datas, bottomTxt, columnsNum, cancleable, outsideTouchable, listener);
    }

    /**
     * 自定义弹出框 默认居中可取消可点击
     *
     * @param context     上下问
     * @param contentView 自定义view
     * @return
     */
    public static BuildBean showCustomAlert(Context context, View contentView) {
        return showCustomAlert(context, contentView, Gravity.CENTER, true, true);
    }

    /**
     * 自定义弹出框 默认可取消可点击
     *
     * @param context     上下文
     * @param contentView 自定义view
     * @param gravity     显示window的位置例如Gravity.center
     * @return
     */
    public static BuildBean showCustomAlert(Context context, View contentView, int gravity) {
        return showCustomAlert(context, contentView, gravity, true, true);
    }

    /***
     * 自定义弹出框
     *
     * @param context          上下文
     * @param contentView      自定义view
     * @param gravity          显示window的位置例如Gravity.center
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @return
     */
    public static BuildBean showCustomAlert(Context context, View contentView, int gravity, boolean cancleable, boolean outsideTouchable) {
        return DialogAssigner.getInstance().assignCustomAlert(context, contentView, gravity, cancleable, outsideTouchable);
    }

    /**
     * 展示PopupWindow
     */
    public static PopupWindowView showPopupWindow(Context context, View target, TdataListener dataListener){
        final PopupWindowView popupWindowView = new PopupWindowView(context, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindowView.initPopupData(dataListener);
        popupWindowView.showing(target);
        return popupWindowView;
    }

    /**
     * 自定义底部弹出框 默认居中可取消可点击
     *
     * @param context     上下问
     * @param contentView 自定义view
     * @return
     */
    public static BuildBean showCustomBottomAlert(Context context, View contentView) {
        return showCustomBottomAlert(context, contentView, true, true);
    }

    /***
     * 自定义底部弹出框
     *
     * @param context          上下文
     * @param contentView      自定义view
     * @param cancleable       true为可以取消false为不可取消
     * @param outsideTouchable true为可以点击空白区域false为不可点击
     * @return
     */
    public static BuildBean showCustomBottomAlert(Context context, View contentView, boolean cancleable, boolean outsideTouchable) {
        return DialogAssigner.getInstance().assignCustomBottomAlert(context, contentView, cancleable, outsideTouchable);
    }


    /**
     * 自定义支付框
     * @param context
     * @param parentView
     */
    public static PopEnterPassword showPayKeyBoard(Context context,View parentView){
        PopEnterPassword popEnterPassword = new PopEnterPassword((Activity) context);
        // 显示窗口
        popEnterPassword.showAtLocation(parentView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        return popEnterPassword;
    }

    /**
     * app升级弹框
     * @param updateAppManager
     * @param updateAppBean
     * @param updateCallback
     */
    public static void showUpdateDialog(UpdateAppManager updateAppManager,
                                        UpdateAppBean updateAppBean,
                                        IUpdateCallback updateCallback){
        updateAppManager.checkUpdate(updateAppBean, updateCallback);
    }
}
