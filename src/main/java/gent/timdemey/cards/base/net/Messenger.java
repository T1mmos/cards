package gent.timdemey.cards.base.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.state.Player;
import gent.timdemey.cards.base.processing.CLT_AbortPickUp;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.processing.ALL_TransferCommand;

public class Messenger {

    public final Gson gson;

    public Messenger() {
        RuntimeTypeAdapterFactory<Command> cmdAdap = RuntimeTypeAdapterFactory.of(Command.class);

        cmdAdap.registerSubtype(CLT_PickUp.class, "PickUp");
        cmdAdap.registerSubtype(CLT_PutDown.class, "PutDown");
        cmdAdap.registerSubtype(CLT_AbortPickUp.class, "PickUpAbort");
        cmdAdap.registerSubtype(ALL_TransferCommand.class, "Transfer");

        this.gson = new GsonBuilder().registerTypeAdapterFactory(cmdAdap).setPrettyPrinting().create();
    }

    public <T> T read(Class<T> clazz, String json) {
        return gson.fromJson(json, clazz);
    }

    public String write(Object obj) {
        return gson.toJson(obj);
    }

    public static void main(String[] args) {
        Player p = new Player("myid", "tim");
        Test t = new Test(p, "testtttt");
        
        String out = new Messenger().write(new Wrapper(t, p));
        System.out.println(out);
    }

    public void send(B_Message msg) {
        System.out.println("SENDING:");
        System.out.println(gson.toJson(msg));
    }
}
