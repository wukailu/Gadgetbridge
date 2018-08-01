package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MutableMediaMetadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import nodomain.freeyourgadget.gadgetbridge.R;

import static com.example.android.uamp.utils.MediaIDHelper.createMediaID;

public class ByGenres implements Assortable {
    public static final String MEDIA_ID = "__BY_GENRE__";
    private ConcurrentMap<String, List<MediaMetadataCompat>> mMusicList;

    enum State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED
    }
    private volatile State mCurrentState = State.NON_INITIALIZED;

    public ByGenres() {
        mMusicList = new ConcurrentHashMap<>();
    }



    @Override
    public String getMEDIA_ID_MUSICS_BY_Key() {
        return "__BY_GENRE__";
    }

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
     * @param key
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
     * @return dataFiled
     */
    @Override
    public String getMetadataField() {
        return MediaMetadataCompat.METADATA_KEY_GENRE;
    }

    @Override
    public void buildListsByKey(ConcurrentMap<String, MutableMediaMetadata> musicListById) {
        mCurrentState = State.INITIALIZING;
        ConcurrentMap<String, List<MediaMetadataCompat>> newMusicListByGenre = new ConcurrentHashMap<>();

        for (MutableMediaMetadata m : musicListById.values()) {
            String genre = m.metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
            List<MediaMetadataCompat> list = newMusicListByGenre.get(genre);
            if (list == null) {
                list = new ArrayList<>();
                newMusicListByGenre.put(genre, list);
            }
            list.add(m.metadata);
        }
        mMusicList = newMusicListByGenre;
        mCurrentState = State.INITIALIZED;
    }

    @Override
    public MediaBrowserCompat.MediaItem createBrowsableMediaItemForRoot(Resources resources) {
        MediaDescriptionCompat description = new MediaDescriptionCompat.Builder()
                .setMediaId(MEDIA_ID)
                .setTitle(resources.getString(R.string.browse_genres))
                .setSubtitle(resources.getString(R.string.browse_genre_subtitle))
                .setIconUri(Uri.parse("android.resource://" +
                        "com.example.android.uamp/drawable/ic_by_genre"))
                .build();
        return new MediaBrowserCompat.MediaItem(description, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE);
    }

    @Override
    public MediaBrowserCompat.MediaItem createBrowsableMediaItemForSubKey(String key, Resources resources) {
        MediaDescriptionCompat description = new MediaDescriptionCompat.Builder()
                .setMediaId(createMediaID(null, MEDIA_ID, key))
                .setTitle(key)
                .setSubtitle(resources.getString(R.string.browse_musics_by_genre_subtitle, key))
                .build();
        return new MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_BROWSABLE);
    }
}
