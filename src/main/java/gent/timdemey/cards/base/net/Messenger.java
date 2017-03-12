package gent.timdemey.cards.base.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.processing.ALL_TransferCommand;
import gent.timdemey.cards.base.processing.CLT_AbortPickUp;
import gent.timdemey.cards.base.processing.CLT_Connect;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;

public class Messenger {

    private static final Gson gson;
    public static final RuntimeTypeAdapterFactory<Command> GSON_COMMAND_ADAPTER;

    static {
        GSON_COMMAND_ADAPTER = RuntimeTypeAdapterFactory.of(Command.class);

        GSON_COMMAND_ADAPTER.registerSubtype(CLT_PickUp.class, "PickUp");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_PutDown.class, "PutDown");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_AbortPickUp.class, "PickUpAbort");
        GSON_COMMAND_ADAPTER.registerSubtype(ALL_TransferCommand.class, "Transfer");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_Connect.class, "Connect");

        gson = new GsonBuilder().registerTypeAdapterFactory(GSON_COMMAND_ADAPTER).create();
    }

    private final String name;
    private final Socket socket;
    private final Writer writer;
    private final BufferedReader reader;
    private final Processor processor;
    private Thread readThr = null;

    public Messenger(String name, Socket s, Processor p) throws IOException {
        this.name = name;
        this.socket = s;
        this.writer = new PrintWriter(s.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.processor = p;
    }

    public void write(B_Message msg) {
        String json = gson.toJson(msg) + "\n";

        try {
            writer.write(json);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        readThr = new Thread(() -> listen(), "Listener :: " + name);
        readThr.start();
    }

    public void stop() {
        readThr.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readThr.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void listen() {
        try {
            while (true) {
                if (!Thread.interrupted()){
                    Thread.currentThread().interrupt();
                    break;
                }
                String line = reader.readLine();
                B_Message msg = gson.fromJson(line, B_Message.class);
                processor.process(msg);
            }
        } catch (IOException e) {
            // 
            e.printStackTrace();
        }
    }
}
