package kh.ad.notifications_listener_service.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import kh.ad.notifications_listener_service.models.DeviceInfoModel;

public class NotificationServiceMethodCallHandler implements MethodChannel.MethodCallHandler {
    private final Context mContext;
    private final String TAG;

    public NotificationServiceMethodCallHandler(Context context, String TAG) {
        this.mContext = context;
        this.TAG = TAG;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.i(TAG, "Got Call " + call.method);
        switch (call.method) {
            case "isNotificationPermissionGranted":
                try {
                    boolean isRunning = NotificationServiceUtils.isNotificationServiceEnabled(mContext);
                    result.success(isRunning);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "requestNotificationPermission":
                try {
                    NotificationServiceUtils.requestPermission(mContext);
                    result.success(true);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "isExist":
                result.success(true);
                break;
            case "getDeviceInfo":
                try {
                    Map<String, Object> map = DeviceInfoModel.getDeviceInfo();
                    result.success(map);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "registerNotificationCallback":
                try {
                    if (call.arguments != null) {
                        long callback = Long.parseLong(call.arguments.toString());
                        SharedPreferencesUtils.getInstance(mContext).saveNotificationCallback(callback);
                        result.success(true);
                    } else {
                        String errMessage = "Callback Method Can't Be Null";
                        result.error("", errMessage, null);
                    }
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "startService":
                try {
                    NotificationServiceUtils.startService(mContext);
                    result.success(null);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "stopService":
                try {
                    NotificationServiceUtils.stopService(mContext);
                    result.success(null);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            case "isServiceRunning":
                try {
                    boolean running = NotificationServiceUtils.isServiceRunning(mContext);
                    result.success(running);
                } catch (Exception e) {
                    result.error(String.valueOf(e.hashCode()), e.getMessage(), null);
                }
                break;
            default:
                Log.i(TAG, "UnImplemented Method Call " + call.method);
                result.notImplemented();
                break;
        }
    }
}
