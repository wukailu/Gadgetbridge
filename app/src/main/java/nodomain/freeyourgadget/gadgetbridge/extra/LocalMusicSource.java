package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MusicProvider;
import com.example.android.uamp.model.MusicProviderSource;
import com.example.android.uamp.utils.MediaIDHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;

public class LocalMusicSource implements MusicProviderSource {
    private ArrayList<MediaMetadataCompat> MusicList;

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        if(MusicList == null)
            MusicList = getLocalMusicList(GBApplication.getContext());
        return MusicList.iterator();
    }

    private List<String> generateEmotionType(){
        //TODO:Modify the tag in a scientific way
        int type1 = (int)(Math.random()*25);
        int type2 = (int)(Math.random()*25);
        while(type2 == type1)
            type2 = (int)(Math.random()*25);
        int type3 = (int)(Math.random()*25);
        while(type3 == type2 || type3 == type1)
            type3 = (int)(Math.random()*25);
        List<String>ret = new ArrayList<String>();
        ret.add(MusicProviderSource.EMOTIONS_TYPES[type1]);
        ret.add(MusicProviderSource.EMOTIONS_TYPES[type2]);
        ret.add(MusicProviderSource.EMOTIONS_TYPES[type3]);
        return ret;
    }

    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    private ArrayList<MediaMetadataCompat> getLocalMusicList(Context context){
        ArrayList<MediaMetadataCompat> list = new ArrayList<MediaMetadataCompat>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)) < 1e5 ||
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)) < 3e4)
                    continue;//过滤大小小于100KB或者时长小于30S的音频

                String songName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                if(songName.contains("-")){
                    songName = songName.split("-")[1];
                }

                List<String> emotions = generateEmotionType();
                MediaMetadataCompat song = new MediaMetadataCompat.Builder()
                        .putString(
                                MediaMetadata.METADATA_KEY_TITLE,
                                songName
                        )
                        .putString(
                                MediaMetadata.METADATA_KEY_ALBUM_ARTIST,
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                        )
                        .putString(
                                MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE,
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        )
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                                String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)).hashCode())
                        )
                        .putLong(
                                MediaMetadata.METADATA_KEY_DURATION,
                                cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                        )
                        .putString(
                                MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION0,
                                emotions.get(0)
                        )
                        .putString(
                                MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION1,
                                emotions.get(1)
                        )
                        .putString(
                                MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION2,
                                emotions.get(2)
                        )
                        .build();


                list.add(song);
            }
            // 释放资源
            cursor.close();
        }

        return list;
    }

}
