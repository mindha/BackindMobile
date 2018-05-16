package backind.backind.Utils;

import com.google.gson.Gson;

public class Utils {
    public static String getJsonfromUrl(Object o){
        final Gson gson = new Gson();
        return gson.toJson(o);
    }
}
