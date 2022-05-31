package me.lake.floatingbili.player.floatingplay;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

/**
 * Created by Lakeinchina(lakeinchina@hotmail.com) on 2015/10/25.
 * FloatingBili Project
 * <p>
 * Copyright (C) 2015 Po Hu <lakeinchina@hotmail.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BiliFloatingPlayService extends Service {
    private final static int BiliFloatPlayServiceID = 127001;
    public final static String TAG = "BiliFloatingPlayService";
    private BiliFloatingView sFloatView;
    private String channelId;

    private void createView(String playUrl, int roomID) {
        sFloatView = new BiliFloatingView(this, playUrl, roomID);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String playUrl = null;
        int roomID = 0;
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                playUrl = bundle.getString("playUrl");
                roomID = bundle.getInt("roomID");
            }
        }
        if (sFloatView == null) {
            NotificationCompat.Builder NBuilder = new NotificationCompat.Builder(this).setSubText("BiliFloatingPlay");
            startForeground(BiliFloatPlayServiceID, NBuilder.build());

            createView(playUrl, roomID);
        } else {
            sFloatView.changeUrl(playUrl);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (sFloatView != null) {
            sFloatView.fixViewPostion();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
