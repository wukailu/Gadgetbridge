package nodomain.freeyourgadget.gadgetbridge.extra;

/*
* getCurrentID IMEI(phone)-MAC(band)
* */

import android.support.v4.media.MediaMetadataCompat;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;

import java.io.OutputStream;

import nodomain.freeyourgadget.gadgetbridge.database.PeriodicExporter;

public class BandAdapter {
    private static String currentPhoneID;
    private static String currentDeviceID;

    public static String getCurrentID(){
        return currentPhoneID + "-" + currentDeviceID;
    }

    public static void setDeviceID(String deviceID){
        currentDeviceID = deviceID;
    }

    public static void setPhoneID(String phoneID){
        currentPhoneID = phoneID;
    }

    public static void exportDBToStream(OutputStream out){
        PeriodicExporter.exportManually(out);
    }


    private static MediaMetadata currentMusic;
    /**
     * Play the current music
     */
    public static void startPlayingMusic(){
        //TODO:Fill the Code
    }

    /**
     * Stop the current playing music
     */
    public static void stopPlayingMusic(){
        //TODO:Fill the Code
    }

    public static void startPlayingMusic(MediaMetadata info){
        currentMusic = info;
        startPlayingMusic();
    }

    public static void startPlayingMusic(MediaMetadataCompat info){
        startPlayingMusic((MediaMetadata)info.getMediaMetadata());
    }

    public static void stopPlayingMusic(MediaMetadata info){
        if(!info.equals(currentMusic)){
            stopPlayingMusic();
            startPlayingMusic(info);
            stopPlayingMusic();
        }
    }

    //当前音乐被切掉
    public static void onSkipToNext(){
        //TODO:handle this
    }

    public static void onTagChanged(String songTitle, int index, String newTag){
        //TODO: Music motion tag has been changed!
    }

    //当前播放类型 本地/在线
    private static int playType = 2;
    public static final int PLAYTYPE_LOCAL = 1;
    public static final int PLAYTYPE_ONLINE = 2;

    public static void setPlayType(int PLAYTYPE){
        playType = PLAYTYPE;
    }

    public static int getPlayType(){
        return playType;
    }
}
