package cn.wildfirechattest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.ivi.imsdk.R;
import com.nwf.sports.ConstantValue;
import com.nwf.sports.chat.login.model.InGameResult;
import com.nwf.sports.chat.login.model.LoginResult;
import com.nwf.sports.ui.activity.MainActivity;
import com.nwf.sports.utils.data.IMDataCenter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.wildfire.chat.kit.ChatManagerHolder;
import ivi.net.base.netlibrary.NetLibrary;
import ivi.net.base.netlibrary.callback.RequestCallBack;
import ivi.net.base.netlibrary.model.ResponseModel;
import ivi.net.base.netlibrary.request.Request;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_test);

        IMDataCenter.getInstance().setLoginName("gmob386");
        IMDataCenter.getInstance().setProductId("A01");


        findViewById(R.id.b_tt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startImService();
            }
        });


    }



    private void inGame(){




        Map<String, Object> params = new HashMap<>();
        params.put("gameCode", "A01095");
        params.put("gameKind", 13);
        params.put("incILog", 1);
        params.put("isWithTransfer", 1);
        params.put("incILog", 1);
        params.put("productId", "A01");
        params.put("verticalApp", 1);
        params.put("loginName", "gmob386");

        Request.with(TestActivity.this).loading().post("http://www.pt-gateway.com/_glaxy_a01_/game/inGame", params, new RequestCallBack<InGameResult>() {
            @Override
            public void onGatewaySuccess(@Nullable InGameResult loginResult, ResponseModel.Head head) {






            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });

    }




    private void startImService(){
        //自己主app 必须是登录状态
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", ChatManagerHolder.gChatManager.getClientId());
        params.put("platform", new Integer(2));
        params.put("productId", "A01");
        params.put("loginName", "gmob386");

        Request.with(TestActivity.this).loading().post("http://www.pt-gateway.com/_glaxy_a01_/im/login", params, new RequestCallBack<LoginResult>() {
            @Override
            public void onGatewaySuccess(@Nullable LoginResult loginResult, ResponseModel.Head head) {

                ChatManagerHolder.gChatManager.connect(loginResult.getUserId(), loginResult.getToken());

                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit()
                        .putString("id", loginResult.getUserId())
                        .putString("token", loginResult.getToken())
                        .apply();

                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);

                RxBus.get().post(ConstantValue.EVENT_TYPE_LOGIN, "login");

            }

            @Override
            public void onGatewayError(Throwable exception) {
                super.onGatewayError(exception);


            }
        });
    }









}
