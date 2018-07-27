package nodomain.freeyourgadget.gadgetbridge.extra;

public class BandAdapter {
    static String currentPhoneID;
    static String currentDeviceID;
    public static String getCurrentID(){
        return currentPhoneID + "-" + currentDeviceID;
    }

    public static void setDeviceID(String deviceID){
        currentDeviceID = deviceID;
    }

    public static void setPhoneID(String phoneID){
        currentPhoneID = phoneID;
    }


}
