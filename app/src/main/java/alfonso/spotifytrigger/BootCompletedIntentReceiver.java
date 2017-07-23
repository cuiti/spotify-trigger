package alfonso.spotifytrigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Alfonso on 14/5/2017.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, LaunchService.class);
            context.startService(pushIntent);
        }
    }
}