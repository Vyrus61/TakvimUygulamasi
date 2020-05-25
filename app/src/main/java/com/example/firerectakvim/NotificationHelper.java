package com.example.firerectakvim;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    CollectionReference notebookRef = FirebaseFirestore.getInstance()
            .collection("Notebook");

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(NewNoteActivity.title)
                .setContentText(NewNoteActivity.description+" Tarih: "+NewNoteActivity.myday+"/"+NewNoteActivity.myMonth+"/"+NewNoteActivity.myYear+" - "+NewNoteActivity.mydayE+"/"+NewNoteActivity.myMonthE+"/"+NewNoteActivity.myYearE+" saat: "+NewNoteActivity.myHour+":"+NewNoteActivity.myMinute)
                .setSmallIcon(R.drawable.ic_notifications);
    }
}