package org.noses.mud.simple.network;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.room.RoomLoader;
import org.noses.mud.simple.session.Session;

import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class TelnetListener implements Runnable {

    int port;

    public TelnetListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        log.info("Running on port {}", port);

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception exc) {
            log.error("Could not listen on port "+port, exc);
            return;
        }

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Session session = new Session();
                Room room = RoomLoader.getInstance().getDefaultRoom();
                session.setRoom(room);
                IOHandler ioHandler = new IOHandler(session, socket.getInputStream(), socket.getOutputStream());
                ioHandler.sendMessage(room.getDisplay("text/plain"));
                ioHandler.run();
            } catch (Exception exc) {
                log.warn("Got an exception listing for a socket", exc);
            }
        }
    }
}
