package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.res.Resources;

import com.example.android.uamp.model.MusicProviderSource;
import com.example.android.uamp.utils.MediaIDHelper;

import nodomain.freeyourgadget.gadgetbridge.R;

public class ByEmotion extends ByKeys {
    public ByEmotion(){
        super();
    }

    @Override
    public String getKEY_MEDIA_ID() {
        return MediaIDHelper.MEDIA_ID_MUSICS_BY_EMONTION;
    }

    @Override
    public String getMetadataField(){
        return MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION0;
    }

    @Override
    public String getKeyTitle(Resources resources){
        return resources.getString(R.string.browse_emotions);
    }

    @Override
    public String getKeySubTitle(Resources resources){
        return resources.getString(R.string.browse_emotions_subtitle);
    }
}
