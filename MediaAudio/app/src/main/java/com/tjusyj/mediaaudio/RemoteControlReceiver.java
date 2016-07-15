package com.tjusyj.mediaaudio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by root on 15/07/16.
 */
public class RemoteControlReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equalsIgnoreCase(Intent.ACTION_MEDIA_BUTTON)) {

            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null)
                return;

            if (event.getKeyCode() != KeyEvent.KEYCODE_HEADSETHOOK &&
                    event.getKeyCode() != KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE &&
                    event.getAction() != KeyEvent.ACTION_DOWN)
                return;

            Log.i("---","----------------keycode-----------------"+event.getKeyCode()+"------");
            Intent i = null;
            switch (event.getKeyCode()) {
        /*
         * one click => play/pause
         * long click => previous
         * double click => next
         */
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    long time = SystemClock.uptimeMillis();
                    switch (event.getAction()) {
                        case KeyEvent.KEYCODE_VOLUME_UP:
                            Log.i("111", "---------V_UP--------");
                            break;
                        case KeyEvent.KEYCODE_MEDIA_NEXT:
                            Log.i("111", "---------next--------");
                            break;
                        case KeyEvent.ACTION_DOWN:
                            Log.i("111", "---------down--------");
                            Log.i("---","----------------keycode-----------------"+event.getKeyCode()+"------");
                            break;
                        case KeyEvent.ACTION_UP:
                            Log.i("111", "---------up--------");
                            break;
                        default:
                            Log.i("---","----------------keycode-----------------"+event.getKeyCode()+"------");
                            break;
                    }
                    break;
                default:
                    Log.i("---","----------------keycode-----------------"+event.getKeyCode()+"------");
                    break;
            }

            if (isOrderedBroadcast())
                abortBroadcast();
            if (i != null)
                context.sendBroadcast(i);
        }
    }
}