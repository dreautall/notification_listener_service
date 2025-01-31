package kh.ad.notifications_listener_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodChannel;
import kh.ad.notifications_listener_service.utils.NotificationServiceMethodCallHandler;
import kh.ad.notifications_listener_service.utils.NotificationServiceFlutterEngineUtils;

/**
 * NotificationsListenerServicePlugin
 */
@SuppressLint("LongLogTag")
public class NotificationsListenerServicePlugin implements FlutterPlugin {
    private MethodChannel channel;
    private NotificationServiceMethodCallHandler handler;
    private final String TAG = "NotificationsListenerServicePlugin";
    private FlutterJNI flutterJNI = new FlutterJNI(); 

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        Log.i(TAG, "On Attached To Engine Start");
        FlutterEngine engine;
        engine = flutterPluginBinding.getFlutterEngine();
        NotificationServiceFlutterEngineUtils.storeEngine(engine);
        
        Context mContext = flutterPluginBinding.getApplicationContext();
        String FOREGROUND_METHOD = "notifications_listener_service/RUN_NATIVE_FOREGROUND_METHOD";
        if(channel == null) {
            channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), FOREGROUND_METHOD);
            handler = new NotificationServiceMethodCallHandler(mContext, TAG);
            channel.setMethodCallHandler(handler);
        }

        flutterJNI.attachToNative();

        Log.i(TAG, "On Attached To Engine End");
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        channel = null;
        handler = null;
        flutterJNI.detachFromNativeAndReleaseResources();
        Log.i(TAG, "On Detached From Engine");
    }
}
