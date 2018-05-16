package backind.backind.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import backind.backind.BackindApps;
import backind.backind.Constant;
import backind.backind.Model.User;
import backind.backind.Response.LoginResponse;

public class SharePreferenceHelper {
    private static SharedPreferences getSP(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getPref(Context context, String key) {
        return getSP(context).getString(key, null);
    }

    /*SAVE AND GET DATA LOGIN*/
    public static void saveDataLogin(Context context, LoginResponse usersModel){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constant.DataLocal.dataUser, BackindApps.getGson().toJson((Object) usersModel)).apply();
    }

    public static LoginResponse getDataUser(Context context) {
        return (LoginResponse) BackindApps.gson.fromJson(getPref(context, Constant.DataLocal.dataUser), LoginResponse.class);
    }
}
