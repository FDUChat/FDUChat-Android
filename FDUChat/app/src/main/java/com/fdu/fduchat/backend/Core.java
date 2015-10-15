package com.fdu.fduchat.backend;

import android.content.Context;
import android.util.Log;

import com.fdu.fduchat.utils.Constant;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.OnMeetingListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import com.yuntongxun.ecsdk.meeting.intercom.ECInterPhoneMeetingMsg;
import com.yuntongxun.ecsdk.meeting.video.ECVideoMeetingMsg;
import com.yuntongxun.ecsdk.meeting.voice.ECVoiceMeetingMsg;

import java.util.List;

public class Core {

    public static void initIMSDK(Context ctx) {
        if(!ECDevice.isInitialized()) {
            ECDevice.initial(ctx, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    //5.0.3的SDK初始参数的方法：ECInitParams params = new ECInitParams();5.1.*以上版本如下：
                    ECInitParams params = ECInitParams.createParams();
                    //自定义登录方式：
                    //测试阶段Userid可以填写手机
                    params.setUserid("18817875743");
                    params.setAppKey("8a48b55150655bee015067269fcd0733");
                    params.setToken("2de3c428f0b0cc1f689dbd31c791ea07");

                    // 设置登陆验证模式（是否验证密码）NORMAL_AUTH-自定义方式
                    params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
                    // 1代表用户名+密码登陆（可以强制上线，踢掉已经在线的设备）
                    // 2代表自动重连注册（如果账号已经在其他设备登录则会提示异地登陆）
                    // 3 LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO）
                    params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);

                    // 设置登陆状态回调
                    params.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
                        public void onConnect() {
                            // 兼容4.0，5.0可不必处理
                        }

                        @Override
                        public void onDisconnect(ECError error) {
                            // 兼容4.0，5.0可不必处理
                        }
                        @Override
                        public void onConnectState(ECDevice.ECConnectState state, ECError error) {
                            if(state == ECDevice.ECConnectState.CONNECT_FAILED ){
                                if(error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                                    //账号异地登陆
                                    Log.d(Constant.LOG_TAG, "IMSDK registeration failed");
                                } else {
                                    //连接状态失败
                                    Log.d(Constant.LOG_TAG, "IMSDK registeration failed");
                                }
                                return ;
                            }
                            else if(state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                                // 登陆成功
                                Log.d(Constant.LOG_TAG, "IMSDK registeration successed");
                            }
                        }
                    });

                    // 设置SDK接收消息回调
                    params.setOnChatReceiveListener(new OnChatReceiveListener() {
                        @Override
                        public void OnReceivedMessage(ECMessage msg) {
                            // 收到新消息
                        }

                        @Override
                        public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
                            // 收到群组通知消息（有人加入、退出...）
                            // 可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
                        }

                        @Override
                        public void onOfflineMessageCount(int count) {
                            // 登陆成功之后SDK回调该接口通知账号离线消息数
                        }

                        @Override
                        public int onGetOfflineMessage() {
                            return 0;
                        }

                        @Override
                        public void onReceiveOfflineMessage(List msgs) {
                            // SDK根据应用设置的离线消息拉去规则通知应用离线消息
                        }

                        @Override
                        public void onReceiveOfflineMessageCompletion() {
                            // SDK通知应用离线消息拉取完成
                        }

                        @Override
                        public void onServicePersonVersion(int version) {
                            // SDK通知应用当前账号的个人信息版本号
                        }

                        @Override
                        public void onReceiveDeskMessage(ECMessage ecMessage) {

                        }

                        @Override
                        public void onSoftVersion(String s, int i) {

                        }
                    });

                    if(params.validate()) {
                        // 判断注册参数是否正确
                        ECDevice.login(params);
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.d(Constant.LOG_TAG, "IMSDK initialization failed");
                }
            });
        }
    }
}
