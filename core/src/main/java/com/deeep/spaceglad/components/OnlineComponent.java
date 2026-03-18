package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.deeep.spaceglad.WiFi.MyClient;
import com.deeep.spaceglad.WiFi.MyRequest;
import com.deeep.spaceglad.WiFi.MyResponse;
import com.deeep.spaceglad.WiFi.MyServer;

public class OnlineComponent implements Component {
    public boolean isServer;
    public MyServer server;
    public MyClient client;

    public OnlineComponent(boolean isServer, MyServer server, MyClient client) {
        this.isServer = isServer;
        this.server = server;
        this.client = client;
    }
}
