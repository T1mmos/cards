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
    
    private final Gson gson;
    
    public Messenger (){
        RuntimeTypeAdapterFactory<Command> cmdAdap = RuntimeTypeAdapterFactory.of(Command.class);

        cmdAdap.registerSubtype(PickUpCommand.class);
        cmdAdap.registerSubtype(PutDownCommand.class);
        cmdAdap.registerSubtype(PickUpAbortCommand.class);
        cmdAdap.registerSubtype(TransferCommand.class);

        this.gson = new GsonBuilder().registerTypeAdapterFactory(cmdAdap).setPrettyPrinting().create();
    }
    
    public void send (Message msg){
        System.out.println("SENDING:");
        System.out.println(gson.toJson(msg));
    }
    
    
}
