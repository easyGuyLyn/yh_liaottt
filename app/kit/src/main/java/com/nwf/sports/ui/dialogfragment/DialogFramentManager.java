package com.nwf.sports.ui.dialogfragment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * <p>类描述：  DialogFrament 管理类
 * <p>创建人：Simon
 * <p>创建时间：2019-02-14
 * <p>修改人：Simon
 * <p>修改时间：2019-02-14
 * <p>修改备注：
 **/
public class DialogFramentManager {
    List<BaseDialogFragment> dialogFragments = new ArrayList<>();
    public static DialogFramentManager mDialogFramentManage;

    public DialogFramentManager() {
        dialogFragments.clear();
    }

    public static DialogFramentManager getInstance() {
        if (mDialogFramentManage == null) {
            mDialogFramentManage = new DialogFramentManager();
        }
        return mDialogFramentManage;
    }


    public void addDialog(BaseDialogFragment dialogFragment) {
        dialogFragments.add(dialogFragment);
    }

    public void removeDialog(BaseDialogFragment dialogFragment) {
        dialogFragments.remove(dialogFragment);
        dialogFragment = null;
    }

    public synchronized void showDialog(FragmentManager supportFragmentManager, BaseDialogFragment dialogFragment) {
        if (!dialogFragments.contains(dialogFragment)) {
            dialogFragments.add(dialogFragment);
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.add(dialogFragment, dialogFragment.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
//            dialogFragment.show(ft, dialogFragment.getClass().getSimpleName());
//            dialogFragment.show(supportFragmentManager, dialogFragment.getClass().getSimpleName());
        } else {
            Log.i("TT", "已经show了");
        }

    }

    /**
     * 关闭全部dialog
     */
    public void clearDialog() {
        for (int i = 0; i < dialogFragments.size(); i++) {
            dialogFragments.get(i).dismiss();
        }
        dialogFragments.clear();
    }

    /**
     * 重后关闭dialog
     *
     * @param num 关闭几个
     */
    public void clearDialog(int num) {
        for (int i = 0; i < num; i++) {
            dialogFragments.get(dialogFragments.size() - 1).dismiss();
            dialogFragments.remove(dialogFragments.size() - 1);
        }
    }

    /**
     * 关闭除了最后一个以外的dialog
     */
    public void retainLastDialog(FragmentManager supportFragmentManager, BaseDialogFragment dialogFragment) {
//        for (int i = 0; i < dialogFragments.size(); i++) {
//            dialogFragments.get(dialogFragments.size() - 2).dismiss();
//            dialogFragments.remove(dialogFragments.size() - 2);
//        }
        if (supportFragmentManager == null || dialogFragment == null) {
            return;
        }
        if (dialogFragments.size() != 0) {
            if (dialogFragments.get(0).getTag().equals("LoginDialogFragment")) {
                return;
            }
        }
        for (DialogFragment data : dialogFragments) {
            data.dismiss();
        }
        dialogFragments.clear();
        showDialog(supportFragmentManager, dialogFragment);
    }

    /**
     * 是否在show dialog
     */
    public boolean isShowLoading(String name) {
        if (dialogFragments.size() == 0) {
            return false;
        }
        boolean isShow = dialogFragments.get(dialogFragments.size() - 1).getTag().equals(name);
        return isShow;
    }


}
