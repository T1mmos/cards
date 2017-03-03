package gent.timdemey.cards.base.pojo;

import com.google.gson.GsonBuilder;

public class PojoUtils {
    public static String pretty (Object obj){
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj).toString();
    }
}
