package com.xrip.can;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import com.xrip.can.Packet;

import java.util.HashMap;
import java.util.Map;

public class PacketsHandler {

    interface PacketHandler {
        void run(Packet packet);
    }

    class Dummy implements PacketHandler {
        @Override
        public void run(Packet packet) {
            // Write your own handler
        }
    }

    class RDSTitle implements PacketHandler {
        @Override
        public void run(Packet packet) {
            String RDSTitle = packet.toString();
            Log.e("citroen_log", "RDS Title: "+ RDSTitle);
        }
    }

    class InfoMessage implements PacketHandler {
        @Override
        public void run(Packet packet) {
            String Message = packet.toString();
                Log.d("citroen_log", "Information Message: "+Message);

        }
    }

    Map<Integer, PacketHandler> handlers = new HashMap<Integer, PacketHandler>();;

    public PacketsHandler() {
        Log.d("citroen_log", "test");
        handlers.put(0xFFF, new Dummy());
        handlers.put(0x1A1, new InfoMessage());
        handlers.put(0x2A5, new RDSTitle());
    }

    public void Handle(Packet packet) throws Exception {
        PacketHandler handler = handlers.get(packet.getId());

        if (handler != null) {
            handler.run(packet);
        } else {
            throw new Exception("Packet without handler");
        }
    }


}

