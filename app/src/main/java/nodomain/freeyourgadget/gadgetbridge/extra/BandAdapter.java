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
    static String currentPhoneID;
    static String currentDeviceID;

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


    static MediaMetadata currentMusic;
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

}
