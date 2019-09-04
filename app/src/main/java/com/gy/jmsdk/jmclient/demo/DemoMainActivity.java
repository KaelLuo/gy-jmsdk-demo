package com.gy.jmsdk.jmclient.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gy.jmsdk.managers.JMSDK;
import com.gy.jmsdk.proxy.Currency;
import com.gy.jmsdk.proxy.ErrorMessage;
import com.gy.jmsdk.stores.core.iap.PayInfo;
import com.gy.jmsdk.stores.core.iap.PayResult;
import com.gy.jmsdk.stores.core.iap.PurchaseCallback;
import com.gy.jmsdk.stores.core.init.ExitCallback;
import com.gy.jmsdk.stores.core.login.LoginCallback;
import com.gy.jmsdk.stores.core.login.UserInfo;
import com.gy.jmsdk.tools.Logger;

import java.util.UUID;

/**
 * <p>
 *     JMSDK 集成示例主 Activity, 包含 JMSDK 主要接口的调用示例，游戏需要在对应的回调中处理自己的逻辑
 * </p>
 * <p>
 *     若想要快速的查看 JMSDK 相关方法的调用示例，请查看一下方法:  {@link #initJM()}, {@link #loginJM()},
 *     {@link #purchaseJM()}, {@link #logoutJM()}, {@link #exitJM()} 以及 {@link #onCreate(Bundle)} 中 JMSDK 接口的调用
 * </p>
 */
public class DemoMainActivity extends Activity implements View.OnClickListener {

    private final String TAG = "JM_SDK_CLIENT";
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        JMSDK.INSTANCE.setDebug(true);
        JMSDK.INSTANCE.setLogLevel(Log.VERBOSE);
        JMSDK.INSTANCE.onCreate(activity, savedInstanceState);
        initViews();
    }

    private void initJM(){
        JMSDK.INSTANCE.init(activity);
    }

    private void loginJM(){
        JMSDK.INSTANCE.login(this, new LoginCallback() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                Toast.makeText(activity, "Login success", Toast.LENGTH_SHORT).show();
                Logger.info(TAG, "JM SDK login callback success: %s", userInfo);
                // 登录成功，游戏可以在这里处理其自己的逻辑，比如将自有账号与渠道账号绑定等
            }

            @Override
            public void onFailure(ErrorMessage message) {
                Toast.makeText(activity, "Login failure " + message.toString(), Toast.LENGTH_SHORT).show();
                Logger.warning(TAG, "JM SDK login callback failure: %s", message);
                // 登录失败，建议游戏根据 ErrorMessage 返回的错误码进行不同处理以及接入排查
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "Login canceled", Toast.LENGTH_SHORT).show();
                Logger.warning(TAG, "JM SDK login callback canceled");
                // 用户取消登录，建议游戏不做任何处理，继续回到调用接入前的界面
            }
        });
    }

    private void purchaseJM(){

        PayInfo info = new PayInfo();
        info.amount = 1; // 商品金额，单位分
        info.notifyUrl = "http://baidu.com"; //CP 支付回调地址
        info.productId = "12344556"; // 支付商品唯一ID
        info.productName = "Lina"; // 支付商品名称
        info.productAmount = 1; // 支付商品数量
        info.productDesc = "Unique lady"; // 商品描述，此描述会显示在用户支付页面
        info.roleId = "88888"; // 角色唯一 ID
        info.roleName = "Kael"; // 角色名称
        info.serviceId = "123454556"; // 角色所在区服务器的唯一ID
        info.transparent = "7758521"; // 支付透传参数，此参数会原样在客户端与服务端支付成功回调中返回
        info.cpOrderId = UUID.randomUUID().toString(); // 支付订单号, 建议CP按一定规则生成
        info.rate = 10; // 支付道具与币种兑换比例，若无则随便设置即可
        info.setCurrency(Currency.CNY);  // 设置支付商品币种

        JMSDK.INSTANCE.purchase(this, info, new PurchaseCallback() {
            @Override
            public void onSuccess(PayResult payResult) {
                Logger.info(TAG, "Purchase Success, result: %s", payResult);
                // 客户端回调支付成功，建议联网游戏，CP以服务端回调作为发货基准，单机游戏 CP 可以此回调来发货
            }

            @Override
            public void onFailure(ErrorMessage message) {
                Logger.warning(TAG, "Purchase Failure: %s", message.toString());
                // 客户端回调支付失败，请根据 message 的 code 进行接入排查或者出错处理
            }

            @Override
            public void onCancel() {
                Logger.info(TAG, "Purchase Cancel");
                // 用户取消支付，建议不处理
            }
        });
    }

    private void logoutJM(){
        JMSDK.INSTANCE.logout(activity);
    }

    private void exitJM(){
        JMSDK.INSTANCE.exit(this, new ExitCallback() {
            @Override
            public void onExit() {
                Logger.info(TAG, "JM SDK exit onExit(), exit the game from now on");
                activity.finish();//finish Main Activity is good enough for sample to exit app
                // 用户选择退出，此处需要正确的调用游戏结束方法， 对于 Demo 结束此 Activity 已经可以正确退出
            }

            @Override
            public void onCancel() {
                Logger.info(TAG, "JM SDK exit api onCancel(), App won't stop");
                // 用户选择取消退出，建议不做任何处理
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.jm_init:
                initJM();
                break;

            case R.id.jm_login:
                loginJM();
                break;

            case R.id.jm_purchase:
                purchaseJM();
                break;

            case R.id.jm_logout:
                logoutJM();
                break;

            case R.id.jm_exit:
                exitJM();
                break;

                default:

        }
    }

    private void initViews(){
        setContentView(R.layout.demo_main);
        findViewById(R.id.jm_init).setOnClickListener(this);
        findViewById(R.id.jm_login).setOnClickListener(this);
        findViewById(R.id.jm_purchase).setOnClickListener(this);
        findViewById(R.id.jm_logout).setOnClickListener(this);
        findViewById(R.id.jm_exit).setOnClickListener(this);
    }




}
