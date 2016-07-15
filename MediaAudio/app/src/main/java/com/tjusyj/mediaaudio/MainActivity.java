package com.tjusyj.mediaaudio;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private MediaPlayer mediaPlayer;
    private EditText edit_name;
    private String filePath;
    private boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_name = (EditText)findViewById(R.id.edit_name);

        mediaPlayer = new MediaPlayer();

        //Use button to control sepcific audio stream
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //AudioService
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        ComponentName mRemoteControlClientReceiverComponent;
        mRemoteControlClientReceiverComponent = new ComponentName(
                getPackageName(), RemoteControlReceiver.class.getName());
        // Start listening for button presses
        am.registerMediaButtonEventReceiver(mRemoteControlClientReceiverComponent);



        //permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously*
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    public void MediaPlay(View v) {
        switch (v.getId()) {
            // 播放按钮
            case R.id.bt_play:
                String fileName = edit_name.getText().toString();
                File audio = new File(Environment.getExternalStorageDirectory(), fileName);
                if (audio.exists()) {// 文件存在
                    filePath = audio.getAbsolutePath(); // 文件绝对路径
                    play(0);
                    edit_name.setText("palying...");
                } else {
                    filePath = null;
                    Toast.makeText(getApplicationContext(), "File not exist!", Toast.LENGTH_LONG).show();
                }
                break;
            // 暂停按钮
            case R.id.bt_pause:
                if (mediaPlayer.isPlaying()) {// 如果正在播放
                    mediaPlayer.pause();// 暂停
                    pause = true;
                    edit_name.setText("pausing...");
                    ((Button) v).setText(R.string.pause_cont);// 文字：暂停-->继续
                } else {
                    if (pause) {// 如果处于暂停状态
                        mediaPlayer.start();// 继续播放
                        pause = false;
                        edit_name.setText("playing...");
                        ((Button) v).setText(R.string.pause_pause);// 文字：继续-->暂停
                    }
                }
                break;
            // 重播按钮
            case R.id.bt_replay:
                if (mediaPlayer.isPlaying()) {
                    edit_name.setText("playing...");
                    mediaPlayer.seekTo(0);// 从开始位置开始播放音乐
                } else {
                    if (filePath != null) {
                        play(0);
                    }
                }
                break;
            // 停止按钮
            case R.id.bt_stop:
                if (mediaPlayer.isPlaying()) {
                    edit_name.setText("name?");
                    mediaPlayer.stop();
                }
                break;
        }
    }

    private void play(int playPosition) {
        try {
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            /**
             *  通过MediaPlayer.setDataSource() 的方法,将URL或文件路径以字符串的方式传入.使用setDataSource ()方法时,要注意以下三点:
             1.构建完成的MediaPlayer 必须实现Null 对像的检查.
             2.必须实现接收IllegalArgumentException 与IOException 等异常,在很多情况下,你所用的文件当下并不存在.
             3.若使用URL 来播放在线媒体文件,该文件应该要能支持pragressive 下载.
             */
            //mediaPlayer.setDataSource(filePath);
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();// 进行缓冲
            mediaPlayer.setOnPreparedListener(new MyPreparedListener(playPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class MyPreparedListener implements
            android.media.MediaPlayer.OnPreparedListener {
        private int playPosition;
        public MyPreparedListener(int playPosition) {
            this.playPosition=playPosition;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();//start播放
            if (playPosition>0) {
                mediaPlayer.seekTo(playPosition);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}

