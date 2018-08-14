package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MusicProvider;
import com.example.android.uamp.model.MutableMediaMetadata;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface Assortable {
    String getKEY_MEDIA_ID();

    /**
     * Get an iterator over the list of keys
     * for example, all kind of genres
     *
     * @return Keys
     */
    Iterable<String> getKeys();

    /**
     * Get music tracks of the given Key
     *
     */
    List<MediaMetadataCompat> getMusicsByKey(String key);

    /**
     * get the dataFiled
     * for genres, return MediaMetadataCompat.METADATA_KEY_GENRE
     *
     * @return dataFiled
     */
    String getMetadataField();

    void buildListsByKey(ConcurrentMap<String, MutableMediaMetadata> musicListById);

    MediaBrowserCompat.MediaItem createBrowsableMediaItemForRoot(Resources resources);

    MediaBrowserCompat.MediaItem createBrowsableMediaItemForSubKey(String key, Resources resources);

}
