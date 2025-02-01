package kh.ad.notifications_listener_service.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.view.FlutterCallbackInformation;

@SuppressLint("LongLogTag")
public class NotificationServiceFlutterEngineUtils {
    private static final String TAG = "NotificationServiceFlutterEngineUtils";
    private static final String FLUTTER_ENGINE_CACHE_KEY = "flutter_engine_main";

    public static FlutterEngine updateEngine(@NonNull Context context, @NonNull FlutterEngine engine) {
        Log.i(TAG, "updateEngine()");
        Long callback = SharedPreferencesUtils.getInstance(context).getNotificationCallback();
        if (callback != null) {
            FlutterCallbackInformation callbackInformation =
                    FlutterCallbackInformation
                            .lookupCallbackInformation(callback);
            DartExecutor.DartCallback dartCallback = new DartExecutor
                    .DartCallback(
                    context.getAssets(),
                    context.getPackageCodePath(),
                    callbackInformation
            );
            engine.getDartExecutor().executeDartCallback(dartCallback);
        }

        if (!engine.getAccessibilityChannel().flutterJNI.isAttached()) {
            FlutterInjector.instance().flutterLoader().startInitialization(context);
            FlutterInjector.instance().flutterLoader()
                    .ensureInitializationComplete(context, new String[]{});
            engine.getAccessibilityChannel().flutterJNI.attachToNative();
        }

        return engine;
    }

    public static FlutterEngine getEngine(@NonNull Context context) {
        Log.i(TAG, "getEngine()");
        FlutterEngine engine;
        engine = FlutterEngineCache.getInstance().get(FLUTTER_ENGINE_CACHE_KEY);

        if (engine != null) {
            Log.i(TAG, "flutter engine returned from cache");
            return engine;
        }

        Log.i(TAG, "flutter engine cache is null, create a new one");
        engine = new FlutterEngine(context);
        engine = updateEngine(context, engine);

        storeEngine(engine);

        return engine;
    }

    public static void storeEngine(@NonNull FlutterEngine engine) {
        Log.i(TAG, "storeEngine()");
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_CACHE_KEY, engine);
    }
}
