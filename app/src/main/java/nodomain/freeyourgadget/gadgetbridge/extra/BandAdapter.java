package nodomain.freeyourgadget.gadgetbridge.extra;

/*
* getCurrentID IMEI(phone)-MAC(band)
* */

import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MusicProvider;
import com.example.android.uamp.utils.LogHelper;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

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

    public static boolean uploadData() {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(temp);
        try {
            writer.write("user=" + getCurrentID() + "&");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exportDBToStream(temp);
        try {
            IOHelper.postToServer("/api/data",temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogHelper.e("uploadData","IOException catched!");
        }
        return false;
    }

    private static String currentMusic;
    private static Long lastTimeTag;

    /**
     * Play the current music
     */
    public static void startPlayingMusic(){
        lastTimeTag = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Stop the current playing music
     */
    public static void stopPlayingMusic(){
        Long timenow = Calendar.getInstance().getTimeInMillis();
        String tnow = Calendar.getInstance().getTime().toString();
        IOHelper.saveData(tnow, currentMusic + "," + lastTimeTag + "," + timenow);
    }

    public static void startPlayingMusic(MediaMetadata info){
        currentMusic = info.getString(MediaMetadata.KEY_ALBUM_TITLE);
        startPlayingMusic();
    }

    public static void startPlayingMusic(MediaMetadataCompat info){
        currentMusic = info.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        startPlayingMusic();
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
    private static int playType = 1;
    public static final int PLAYTYPE_LOCAL = 1;
    public static final int PLAYTYPE_ONLINE = 2;

    public static void setPlayType(int PLAYTYPE){
        playType = PLAYTYPE;
    }

    public static int getPlayType(){
        return playType;
    }

    private static MusicProvider musicProvider;
    public static void setMusicProivder(MusicProvider proivder){
        musicProvider = proivder;
    }

    public static MediaMetadataCompat getMusic(@Nullable String musicId){
        if(musicId == null) return null;
        return musicProvider.getMusic(musicId);
    }

    //主动播放某种音乐
    public static void playCertainMusic(String categoryType, String categoryValue){
        String timenow = Calendar.getInstance().getTime().toString();
        IOHelper.saveData(timenow, "Start Playing Mood:" + categoryValue);
    }
}
