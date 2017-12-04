package jp.issei.omizu.weightcalendar.presentation.model;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by isseiomizu on 2017/03/20.
 */

public class SoundController {
    private Context mContext = null;
    private Ringtone mRingtone = null;

    private int mStopTime = 0;

    private final Handler handler = new Handler();
    private final Runnable r = new Runnable() {

        private boolean mRun = false;

        @Override
        public void run() {
            if (mRun) {
                stop();
                mRun = false;
                return;
            }
            handler.postDelayed(this, mStopTime);
            mRun = true;
        }
    };


    public SoundController(Context context) {
        mContext = context;
    }

    private void play() {
        if (mRingtone.isPlaying()) {
            stop();
        }

        // 再生
        mRingtone.play();
    }

    private void stop() {
        handler.removeCallbacks(r);
        if (mRingtone != null) {
            mRingtone.stop();
        }
    }

    private void play(int seconds) {
        // 再生
        play();
        stop(seconds);
    }

    private void stop(int seconds) {
        mStopTime = seconds * 1000;
        handler.post(r);
    }

    public void forceStop() {
        // 停止
        stop();
    }

    public void playStartSound() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mRingtone = RingtoneManager.getRingtone(mContext, uri);

        // 再生
        play(2);
    }

    public void playTrainingEndSound() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mRingtone = RingtoneManager.getRingtone(mContext, uri);

        // 再生
        play(2);
    }

    public void playPastSecondsSound() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mRingtone = RingtoneManager.getRingtone(mContext, uri);

        // 再生
        play(5);
    }

}

