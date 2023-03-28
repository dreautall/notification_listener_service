package kh.ad.notifications_listener_service.models;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class NotificationModel {
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private static final String NOTIFICATION_POST_TIME = "NOTIFICATION_POST_TIME";
    private static final String NOTIFICATION_KEY = "NOTIFICATION_KEY";
    private static final String NOTIFICATION_TAG = "NOTIFICATION_TAG";
    private static final String NOTIFICATION_PACKAGE_NAME = "NOTIFICATION_PACKAGE_NAME";
    private static final String NOTIFICATION_STATE = "NOTIFICATION_STATE";
    private static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    private static final String NOTIFICATION_TEXT = "NOTIFICATION_TEXT";

    public static Map<String, Object> fromStatusBarNotification(StatusBarNotification sbn, String state) {
        Map<String, Object> map = new HashMap<>();
        if (sbn == null) return null;
        map.put(NOTIFICATION_ID, sbn.getId());
        map.put(NOTIFICATION_POST_TIME, sbn.getPostTime());
        map.put(NOTIFICATION_KEY, sbn.getKey());
        map.put(NOTIFICATION_TAG, sbn.getTag());
        map.put(NOTIFICATION_PACKAGE_NAME, sbn.getPackageName());
        map.put(NOTIFICATION_STATE, state);
        Bundle extras = sbn.getNotification().extras;
        if (extras != null) {
            map.put(NOTIFICATION_TITLE, extras.getCharSequence(Notification.EXTRA_TITLE));
            map.put(NOTIFICATION_TEXT, extras.getCharSequence(Notification.EXTRA_TEXT));
        }
        return map;
    }
}
