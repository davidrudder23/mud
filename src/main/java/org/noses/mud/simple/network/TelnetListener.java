package org.noses.mud.simple.network;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.room.RoomLoader;
import org.noses.mud.simple.user.Session;
import org.noses.mud.simple.user.SessionRegistry;
import org.noses.mud.simple.user.User;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

/**
 * Eventually, I'd like to have multiple different ways to connect. "telnet hostname 2323" was the way we used to do it, back
 * in the day.  I'd like to add ssh pretty quickly.  If this thing gets popular, a json representation would be nice, so we
 * can make cell phone or web frontends.
 */
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

                // TODO
                User user = new User();
                user.setName("User "+ UUID.randomUUID().toString());
                user.setDescription("of the incomplete profile");

                Session session = new Session(user);
                Room room = RoomLoader.getInstance().getDefaultRoom();
                session.setRoom(room);
                SessionRegistry.getInstance().register(session);

                IOHandler ioHandler = new IOHandler(session, socket.getInputStream(), socket.getOutputStream());
                session.setIoHandler(ioHandler);
                ioHandler.sendMessage(room.getDisplay("text/plain"));

                // This is just plain old Java threads.  There's probably a much more efficient way to do
                // this, but it's nice to have something so simple

                new Thread(ioHandler).start();
            } catch (Exception exc) {
                log.warn("Got an exception listing for a socket", exc);
            }
        }
    }
}
