package gent.timdemey.cards.base.beans;

import com.google.gson.GsonBuilder;

import gent.timdemey.cards.base.net.Connection;

public class BeanUtils {
    public static String pretty(Object obj) {
        
        return new GsonBuilder().registerTypeAdapterFactory(Connection.GSON_COMMAND_ADAPTER).setPrettyPrinting().create().toJson(obj);
    }
    
    public static String small (Object obj) {
        return new GsonBuilder().create().toJson(obj).toString();
    }

}
