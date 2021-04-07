package com.example.myapplication.uiView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;

public class Video_3_2Activity extends AppCompatActivity implements View.OnClickListener {

    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_3_2);
        findViewById(R.id.bt_luzhi).setOnClickListener(this);
        findViewById(R.id.bt_yasuo).setOnClickListener(this);

    }

    private void initVideo() {
        // 录制
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()

                .fullScreen(false)
                .smallVideoWidth(360)
                .smallVideoHeight(480)
                .recordTimeMax(6000)
                .recordTimeMin(1500)
                .maxFrameRate(20)
                .videoBitrate(600000)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, SendSmallVideoActivity.class.getName(), config);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt_luzhi:
                initVideo();
                break;
                case R.id.bt_yasuo:
                localCompress();
                break;
        }
    }

    private void localCompress() {
        // 选择本地视频压缩
        LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
        final LocalMediaConfig config = buidler
                .setVideoPath(path)
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode())
                .setFramerate(15)
                .setScale(1.0f)
                .build();
        OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
    }
}