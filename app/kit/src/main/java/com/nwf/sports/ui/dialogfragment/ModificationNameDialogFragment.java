package com.nwf.sports.ui.dialogfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dawoo.coretool.util.SPTool;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.utils.data.DataCenter;

import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import cn.wildfire.chat.kit.common.OperateResult;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.model.ModifyMyInfoEntry;
import cn.wildfirechat.model.UserInfo;

import static cn.wildfirechat.model.ModifyMyInfoType.Modify_DisplayName;

/**
 * <p>类描述： 修改用户名
 * <p>创建人：Simon
 * <p>创建时间：2019-03-25
 * <p>修改人：Simon
 * <p>修改时间：2019-03-25
 * <p>修改备注：
 **/
public class ModificationNameDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_titleupgrade)
    TextView mTvTitleupgrade;
    @BindView(R.id.tv_msg_upgrade)
    EditText mTvMsgUpgrade;
    @BindView(R.id.btn_close)
    Button mBtnClose;
    @BindView(R.id.tv_relation)
    Button mTvRelation;

    private String title = "";
    private String content = "";

    private UserViewModel userViewModel;
    private UserInfo userInfo;
    SucceedListener mSucceedListener = null;


    public void setSucceedListener(SucceedListener succeedListener) {
        mSucceedListener = succeedListener;
    }

    @Override
    protected int getViewId() {
        return R.layout.dialogfragment_modification_name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsOutCanback = true;
        intScreenWProportion = 1;
        AnimationsStyle = -1;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        if (!TextUtils.isEmpty(content)) {
            mTvMsgUpgrade.setText(content);
        }
        if (!TextUtils.isEmpty(title)) {
            mTvTitleupgrade.setText(title);
        }

        mTvRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMyName();
            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userInfo = userViewModel.getUserInfo(userViewModel.getUserId(), true);

        if (userInfo == null) {
            Toast.makeText(getActivity(), "用户不存在", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }

//        mTvMsgUpgrade.setHint(userInfo.displayName);
//        mTvMsgUpgrade.setSelection(mTvMsgUpgrade.getText().toString().trim().length());
    }


    LoadingDialogFragment loadingDialogFragment = null;

    private void changeMyName() {
        if (TextUtils.isEmpty(mTvMsgUpgrade.getText().toString())) {
            showMessage("请输入修改的用户名");
            return;
        }
        loadingDialogFragment = new LoadingDialogFragment();
        DialogFramentManager.getInstance().showDialog(getActivity().getSupportFragmentManager(), loadingDialogFragment);
        String nickName = mTvMsgUpgrade.getText().toString().trim();
        ModifyMyInfoEntry entry = new ModifyMyInfoEntry(Modify_DisplayName, nickName);
        userViewModel.modifyMyInfo(Collections.singletonList(entry)).observe(this, new Observer<OperateResult<Boolean>>() {
            @Override
            public void onChanged(@Nullable OperateResult<Boolean> booleanOperateResult) {
                if (booleanOperateResult.isSuccess()) {
                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    SPTool.put(DataCenter.getInstance().getUserInfoBean().username + ConstantValue.MODIFICATION_NAME, true);
                    if (mSucceedListener != null) {
                        mSucceedListener.succeed();
                    }
                    loadingDialogFragment.dismissAllowingStateLoss();
                    dismissAllowingStateLoss();
                } else {
                    loadingDialogFragment.dismissAllowingStateLoss();
                    Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    public ModificationNameDialogFragment setTvTitle(String title) {
        this.title = title;
        return this;
    }

    public ModificationNameDialogFragment setTvContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public interface SucceedListener {
        void succeed();
    }
}
