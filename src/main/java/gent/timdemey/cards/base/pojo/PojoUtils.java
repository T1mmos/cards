package gent.timdemey.cards.base.pojo;

import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.cmd.Command;
import gent.timdemey.cards.base.cmd.PickUpAbortCommand;
import gent.timdemey.cards.base.cmd.PickUpCommand;
import gent.timdemey.cards.base.cmd.PutDownCommand;
import gent.timdemey.cards.base.cmd.TransferCommand;

public class PojoUtils {
    public static String pretty(Object obj) {
        RuntimeTypeAdapterFactory<Command> cmdAdap = RuntimeTypeAdapterFactory.of(Command.class);

        cmdAdap.registerSubtype(PickUpCommand.class);
        cmdAdap.registerSubtype(PutDownCommand.class);
        cmdAdap.registerSubtype(PickUpAbortCommand.class);
        cmdAdap.registerSubtype(TransferCommand.class);
        
        
        return new GsonBuilder().registerTypeAdapterFactory(cmdAdap).setPrettyPrinting().create().toJson(obj);
    }
    
    public static String small (Object obj) {
        return new GsonBuilder().create().toJson(obj).toString();
    }

}
