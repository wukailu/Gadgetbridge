package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MusicProvider;
import com.example.android.uamp.model.MusicProviderSource;

import java.util.ArrayList;
import java.util.Iterator;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;

public class LocalMusicSourceProvider implements MusicProviderSource {
    private ArrayList<MediaMetadataCompat> MusicList;

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        if(MusicList == null)
            MusicList = getLocalMusicList(GBApplication.getContext());
        return MusicList.iterator();
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

                MediaMetadataCompat song = new MediaMetadataCompat.Builder()
                        .putString(
                                MediaMetadata.METADATA_KEY_TITLE,
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                        )
                        .putString(
                                MediaMetadata.METADATA_KEY_ALBUM_ARTIST,
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                        )
                        .putString(
                                MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE,
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        )
                        .putLong(
                                MediaMetadata.METADATA_KEY_DURATION,
                                cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                        ).build();

                list.add(song);
            }
            // 释放资源
            cursor.close();
        }

        return list;
    }

}
