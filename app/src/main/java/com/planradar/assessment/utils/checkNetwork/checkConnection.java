package com.planradar.assessment.utils.checkNetwork;

import android.app.Activity;


public class checkConnection {
    private static checkConnection iAppUtilities = null;
    public static Activity mActivity;

    public static checkConnection getInstance(){
        if(iAppUtilities == null){
            iAppUtilities = new checkConnection(mActivity);
        }

        return iAppUtilities;
    }
    checkConnection(Activity a) {
        this.mActivity = a;
    }

    /**
     * Check if internet is available
     * @param aActivity The android Context instance.
     * @return true or false
     */
    public  boolean checkNetWork(Activity aActivity){
        switch (NetworkUtil.getConnectivityStatus(aActivity)) {
            case OFFLINE:
                MessageUtility.showToast(aActivity,"No Internet Connection");
                return false;
            case WIFI_CONNECTED_WITHOUT_INTERNET:
                MessageUtility.showToast(aActivity,"No Internet Connection");
                return false;
            case MOBILE_DATA_CONNECTED:
            case WIFI_CONNECTED_WITH_INTERNET:
                return true;
            case UNKNOWN:
                MessageUtility.showToast(aActivity,"No Internet Connection");
                return false;
            default:
                return false;
        }
    }
}
