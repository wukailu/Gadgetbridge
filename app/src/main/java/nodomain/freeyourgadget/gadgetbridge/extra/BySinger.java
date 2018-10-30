package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.utils.MediaIDHelper;

import nodomain.freeyourgadget.gadgetbridge.R;

public class BySinger extends ByKeys {
    @Override
    public String getKeyTitle(Resources resources) {
        return resources.getString(R.string.browse_singer);
    }

    @Override
    public String getKeySubTitle(Resources resources) {
        return resources.getString(R.string.browse_singer_subtitle);
    }

    @Override
    public String getKEY_MEDIA_ID() {
        return MediaIDHelper.MEDIA_ID_MUSICS_BY_SIGNER;
    }

    /**
     * get the dataFiled
     * for genres, return MediaMetadataCompat.METADATA_KEY_GENRE
     *
     * @return dataFiled
     */
    @Override
    public String getMetadataField() {
        return MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST;
    }
}
