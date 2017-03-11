package gent.timdemey.cards.base.beans;

import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.CLT_AbortPickUp;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.processing.ALL_TransferCommand;

public class PojoUtils {
    public static String pretty(Object obj) {
        RuntimeTypeAdapterFactory<Command> cmdAdap = RuntimeTypeAdapterFactory.of(Command.class);

        cmdAdap.registerSubtype(CLT_PickUp.class);
        cmdAdap.registerSubtype(CLT_PutDown.class);
        cmdAdap.registerSubtype(CLT_AbortPickUp.class);
        cmdAdap.registerSubtype(ALL_TransferCommand.class);
        
        
        return new GsonBuilder().registerTypeAdapterFactory(cmdAdap).setPrettyPrinting().create().toJson(obj);
    }
    
    public static String small (Object obj) {
        return new GsonBuilder().create().toJson(obj).toString();
    }

}
