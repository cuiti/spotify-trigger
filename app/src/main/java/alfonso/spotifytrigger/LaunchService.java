package alfonso.spotifytrigger;

import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LaunchService extends Service {

    private MusicIntentReceiver musicIntentReceiver;

    @Override public IBinder onBind(Intent intent) {
        // We don't provide binding, so
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        Log.d("debug", "Service created, starting MusicIntentReceiver");
        musicIntentReceiver = new MusicIntentReceiver();
        registerReceiver(musicIntentReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicIntentReceiver);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d("service debug", "Headset is unplugged");
                        break;
                    case 1:
                        Log.d("service debug", "Headset is plugged");
                        try{
                            startActivity(
                                    new Intent(Intent.ACTION_VIEW, Uri.parse("spotify:track:"))
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } catch (ActivityNotFoundException e){
                            startActivity(
                                    new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=com.spotify.music"))
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        break;
                    default:
                        Log.d("service debug", "wat");
                }
            }
        }
    }
}