package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.utils.MediaIDHelper;

import nodomain.freeyourgadget.gadgetbridge.R;

public class ByGenres extends ByKeys {
    public ByGenres(){
        super();
    }

    @Override
    public String getKEY_MEDIA_ID() {
        return MediaIDHelper.MEDIA_ID_MUSICS_BY_GENRE;
    }

    @Override
    public String getMetadataField() {
        return MediaMetadataCompat.METADATA_KEY_GENRE;
    }

    @Override
    public String getKeyTitle(Resources resources){
        return resources.getString(R.string.browse_genres);
    }

    @Override
    public String getKeySubTitle(Resources resources){
        return resources.getString(R.string.browse_genre_subtitle);
    }
}
