package nodomain.freeyourgadget.gadgetbridge.extra;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;

import com.example.android.uamp.model.RemoteJSONSource;
import com.example.android.uamp.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;

public class RemoteMusicSourceProvider extends RemoteJSONSource {
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
        return builder;
    }

}
