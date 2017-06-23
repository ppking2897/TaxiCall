package com.example.biancaen.texicall.notificaiton;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.R;
import com.google.firebase.messaging.FirebaseMessagingService;

public class HBMessageService extends FirebaseMessagingService {
    String TAG = getClass().getName();
    private final int REQUEST_CODE = 200;
    private final int INTENT_ID = 100;
    //若要變動要另設規則，影響通知那邊的ID號碼，重複會被覆蓋
    //新版zzm已不可用

    @Override
    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
        receiveMessageFirst(intent);
    }

    //收到FCM時第一階段在此處哩,不分前後台
    public void receiveMessageFirst(Intent intent) {
//        Log.i("底層",intent.toString());
        String msg = intent.getStringExtra("gcm.notification.body");  // 原型訊息
        //鍵值資料
        String title = intent.getStringExtra("title");
        String tasknumber = intent.getStringExtra("tasknumber");
        String body = intent.getStringExtra("body");

        Log.i(TAG , msg+"+++++"+title+"+++++"+tasknumber+"+++++"+body);
        sendNotification(title ,body ,tasknumber ,MainMenuActivity.class);
    }

    //送出通知 API 21 以下要額外做處裡 通知訊息22以後android有翻版過
    private void sendNotification(String title , String body , String tasknumber , Class activityClass) {

        Intent launchIntent = new Intent(this, activityClass);
        launchIntent.setAction(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivities(this,REQUEST_CODE,new Intent[]{launchIntent},0);

        Bitmap bm = BitmapFactory.decodeResource(getResources() , R.mipmap.ic_launcher);
        Notification notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bm)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(INTENT_ID,notificationBuilder);

//        // 將此通知放到通知欄的"Ongoing"即"正在運行"組中
//        notification.flags = Notification.FLAG_ONGOING_EVENT;
//
//        // 表明在點擊了通知欄中的"清除通知"後，此通知不清除，
//        // 經常與FLAG_ONGOING_EVENT一起使用
//        notification.flags = Notification.FLAG_NO_CLEAR;
//
//        //閃爍燈光
//        notification.flags = Notification.FLAG_SHOW_LIGHTS;
//
//        // 重複的聲響,直到用戶響應。
//        notification.flags = Notification.FLAG_INSISTENT;
//
//
//        // 把指定ID的通知持久的發送到狀態條上.
//        mNotificationManager.notify(0, notification);

        // 取消以前顯示的一個指定ID的通知.假如是一個短暫的通知，
        // 試圖將之隱藏，假如是一個持久的通知，將之從狀態列中移走.
//              mNotificationManager.cancel(0);

        //取消以前顯示的所有通知.
//              mNotificationManager.cancelAll();






    }

}
