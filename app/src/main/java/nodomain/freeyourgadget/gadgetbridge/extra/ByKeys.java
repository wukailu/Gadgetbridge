package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MutableMediaMetadata;
import com.example.android.uamp.utils.MediaIDHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import nodomain.freeyourgadget.gadgetbridge.R;

import static com.example.android.uamp.utils.MediaIDHelper.createMediaID;

public abstract class ByKeys implements Assortable {
    private ConcurrentMap<String, List<MediaMetadataCompat>> mMusicList;

    enum State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED
    }
    private volatile State mCurrentState = State.NON_INITIALIZED;

    public ByKeys() {
        mMusicList = new ConcurrentHashMap<>();
    }

    abstract public String getKeyTitle(Resources resources);

    abstract public String getKeySubTitle(Resources resources);

    /**
     * Get an iterator over the list of keys
     * for example, all kind of genres
     *
     * @return Keys
     */
    @Override
    public Iterable<String> getKeys() {
        if (mCurrentState != State.INITIALIZED) {
            return Collections.emptyList();
        }
        return mMusicList.keySet();
    }

    /**
     * Get music tracks of the given Key
     *
     * @param key a key
     */
    @Override
    public List<MediaMetadataCompat> getMusicsByKey(String key) {
        if (mCurrentState != State.INITIALIZED || !mMusicList.containsKey(key)) {
            return Collections.emptyList();
        }
        return mMusicList.get(key);
    }

    /**
     * get the dataFiled
     * for genres, return MediaMetadataCompat.METADATA_KEY_GENRE
     *
     */

    @Override
    public void buildListsByKey(ConcurrentMap<String, MutableMediaMetadata> musicListById) {
        mCurrentState = State.INITIALIZING;
        ConcurrentMap<String, List<MediaMetadataCompat>> newMusicListByGenre = new ConcurrentHashMap<>();

        for (MutableMediaMetadata m : musicListById.values()) {
            String key = m.metadata.getString(getMetadataField());
            if(key == null) key = "null";
            List<MediaMetadataCompat> list = newMusicListByGenre.get(key);
            if (list == null) {
                list = new ArrayList<>();
                newMusicListByGenre.put(key, list);
            }
            list.add(m.metadata);
        }
        mMusicList = newMusicListByGenre;
        mCurrentState = State.INITIALIZED;
    }

    @Override
    public MediaBrowserCompat.MediaItem createBrowsableMediaItemForRoot(Resources resources) {
        MediaDescriptionCompat description = new MediaDescriptionCompat.Builder()
                .setMediaId(getKEY_MEDIA_ID())
                .setTitle(getKeyTitle(resources))
                .setSubtitle(getKeySubTitle(resources))
                .setIconUri(Uri.parse("android.resource://" +
                        "com.example.android.uamp/drawable/ic_by_genre"))
                .build();
        return new MediaBrowserCompat.MediaItem(description, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE);
    }

    @Override
    public MediaBrowserCompat.MediaItem createBrowsableMediaItemForSubKey(String key, Resources resources) {
        MediaDescriptionCompat description = new MediaDescriptionCompat.Builder()
                .setMediaId(createMediaID(null, getKEY_MEDIA_ID(), key))
                .setTitle(key)
                .setSubtitle(resources.getString(R.string.browse_musics_by_genre_subtitle, key))
                .build();
        return new MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_BROWSABLE);
    }
}
