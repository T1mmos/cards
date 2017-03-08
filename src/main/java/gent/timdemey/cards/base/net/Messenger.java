package gent.timdemey.cards.base.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.cmd.Command;
import gent.timdemey.cards.base.cmd.PickUpAbortCommand;
import gent.timdemey.cards.base.cmd.PickUpCommand;
import gent.timdemey.cards.base.cmd.PutDownCommand;
import gent.timdemey.cards.base.cmd.TransferCommand;
import gent.timdemey.cards.base.pojo.Message;

public class Messenger {

    public final Gson gson;

    public Messenger() {
        RuntimeTypeAdapterFactory<Command> cmdAdap = RuntimeTypeAdapterFactory.of(Command.class);

        cmdAdap.registerSubtype(PickUpCommand.class, "PickUp");
        cmdAdap.registerSubtype(PutDownCommand.class, "PutDown");
        cmdAdap.registerSubtype(PickUpAbortCommand.class, "PickUpAbort");
        cmdAdap.registerSubtype(TransferCommand.class, "Transfer");

        this.gson = new GsonBuilder().registerTypeAdapterFactory(cmdAdap).setPrettyPrinting().create();
    }

    public <T> T read(Class<T> clazz, String json) {
        return gson.fromJson(json, clazz);
    }
    
    public String write (Object obj){
        return gson.toJson(obj);
    }

    public void send(Message msg) {
        System.out.println("SENDING:");
        System.out.println(gson.toJson(msg));
    }
}
