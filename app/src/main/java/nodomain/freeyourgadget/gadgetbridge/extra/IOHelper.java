package nodomain.freeyourgadget.gadgetbridge.extra;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;

public class IOHelper {
    /**
     * 将字符串数据保存到本地
     * @param context 上下文
     * @param filename 生成XML的文件名
     * @param map <生成XML中每条数据名,需要保存的数据>
     */
    public static void saveSettingNote(Context context, String filename , Map<String, String> map) {
        SharedPreferences.Editor note = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            note.putString(entry.getKey(), entry.getValue());
        }
        note.apply();
    }

    /**
     * 从本地取出要保存的数据
     * @param context 上下文
     * @param filename 文件名
     * @param dataname 生成XML中每条数据名
     * @return 对应的数据(找不到为NUll)
     */
    public static String getSettingNote(Context context,String filename ,String dataname) {
        SharedPreferences read = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return read.getString(dataname, null);
    }

    public static void saveData(String key,String value){
        Map<String,String> t = new HashMap<>();
        t.put(key,value);
        saveSettingNote(GBApplication.getContext(),"temp",t);
    }

    public static String getData(String key){
        return getSettingNote(GBApplication.getContext(),"temp",key);
    }

    private static final String baseURL = "http://www.wuvin.win/";
    public static void postToServer(String port, String data) throws IOException {
        URL reqURL = new URL(baseURL + port); //the URL we will send the request to
        HttpURLConnection request = (HttpURLConnection) (reqURL.openConnection());
        request.setDoOutput(true);
        request.addRequestProperty("Content-Length", Integer.toString(data.length())); //add the content length of the post data
        request.addRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //add the content type of the request, most post data is of this type
        request.setRequestMethod("POST");
        request.setConnectTimeout(3000);//3s
        request.connect();
        OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream()); //we will write our request data here
        writer.write(data);
        writer.flush();
        writer.close();
    }

    public static String getFromServer(String port, String params) throws IOException {
        URL reqURL = new URL(baseURL + port + "?" + params); //the URL we will send the request to
        HttpURLConnection request = (HttpURLConnection) (reqURL.openConnection());
        request.setRequestMethod("GET");
        request.connect();
        int responseCode = request.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            //得到响应流
            InputStream inputStream = request.getInputStream();
            //将响应流转换成字符串
            return inputStream.toString();
        }
        return null;
    }
}
