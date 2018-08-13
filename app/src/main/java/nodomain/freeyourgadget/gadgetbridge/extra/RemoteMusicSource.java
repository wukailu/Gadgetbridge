package nodomain.freeyourgadget.gadgetbridge.extra;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.MusicProviderSource;
import com.example.android.uamp.model.RemoteJSONSource;
import com.example.android.uamp.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;

public class RemoteMusicSource extends RemoteJSONSource {
    //TODO: 填入服务器链接
    private static final String CATALOG_URL = "http://storage.googleapis.com/automotive-media/music.json";

    @Override
    public String getCATALOG_URL() {
        return CATALOG_URL;
    }

    @Override
    protected MediaMetadataCompat.Builder constructJSONBuilder(JSONObject json, String basePath) throws JSONException {
        MediaMetadataCompat.Builder builder = super.constructJSONBuilder(json, basePath);
        //TODO: add extra massege
        List<String> emotions = generateEmotionType();
        builder.putString(MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION0, emotions.get(0));
        builder.putString(MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION1, emotions.get(1));
        builder.putString(MusicProviderSource.CUSTOM_METADATA_KEY_EMONTION2, emotions.get(2));
        return builder;
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
}
