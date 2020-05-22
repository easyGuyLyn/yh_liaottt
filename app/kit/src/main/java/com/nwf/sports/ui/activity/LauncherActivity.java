package com.nwf.sports.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.dawoo.coretool.util.SPTool;
import com.nwf.sports.IMApplication;
import com.nwf.sports.ConstantValue;
import com.ivi.imsdk.R;
import com.nwf.sports.net.RetrofitHelper;
import com.nwf.sports.utils.line.LineHelperService;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-07-04
 * <p>修改人：Simon
 * <p>修改时间：2019-07-04
 * <p>修改备注：
 **/
public class LauncherActivity extends BaseActivity {

    private Handler handler = new Handler();
    private int DELAY = 5 * 1000;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void initViews() {
//        RetrofitHelper.setClientDomain("http://10.91.6.23:8080/");
        RetrofitHelper.clearDomainUrl();
        String url = (String) SPTool.get(ConstantValue.SAVA_URL, "");
        if (TextUtils.isEmpty(url)) {
            LineHelperService.startService(LauncherActivity.this);
        } else {
            IMApplication.isGain = true;
            RetrofitHelper.setClientDomain(url);
        }
        handler.postDelayed(task, DELAY);
    }

    @Override
    protected void initData() {

    }


    private Runnable task = new Runnable() {
        @Override
        public void run() {
            enterMain();
        }
    };

    public void enterMain() {
        handler.removeCallbacks(task);
//        if (!IMDataCenter.getInstance().getUserInfoBean().isRealLogin) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//        }
        finish();
    }
}