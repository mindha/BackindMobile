package backind.backind;

import android.app.Application;

import com.google.gson.Gson;

public class BackindApps extends Application{
    public static Gson gson;

    static {
        gson = new Gson();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static  Gson getGson(){
        return gson;
    }
}
