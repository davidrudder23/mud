package org.noses.mud.simple.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.shell.ShellFactory;
import org.apache.sshd.server.shell.TtyFilterInputStream;
import org.apache.sshd.server.shell.TtyFilterOutputStream;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.room.RoomLoader;
import org.noses.mud.simple.session.Session;
import org.noses.mud.simple.session.SessionRegistry;

import java.io.*;
import java.util.Map;

@Slf4j
public class SSHStreamFactory implements ShellFactory  {
    public Command createShell(ChannelSession channelSession) throws IOException {
        return new SSHStream();
    }

    public static class SSHStream implements Command, Runnable {

        private InputStream in;
        private OutputStream out;
        private OutputStream err;
        private ExitCallback callback;
        private Environment environment;
        private Thread thread;

        public InputStream getInputStream() {
            return in;
        }

        public OutputStream getOutputStream() {
            return out;
        }

        public OutputStream getErr() {
            return err;
        }

        public Environment getEnvironment() {
            return environment;
        }

        public void setInputStream(InputStream in) {
            this.in = in;
        }

        public void setOutputStream(OutputStream out) {
            this.out = out;
        }

        public void setErrorStream(OutputStream err) {
            this.err = err;
        }

        public void setExitCallback(ExitCallback callback) {
            this.callback = callback;
        }

        public void start(Environment env) throws IOException {
            environment = env;
            thread = new Thread(this, "SSHStream");
            thread.start();
        }

        public void destroy() {
            thread.interrupt();
        }

        @Override
        public void start(ChannelSession channelSession, Environment environment) throws IOException {
            start(environment);
        }

        @Override
        public void destroy(ChannelSession channelSession) throws Exception {

        }

        public void run() {
            Session session = new Session();

            Room room = RoomLoader.getInstance().getDefaultRoom();
            session.setRoom(room);
            SessionRegistry.getInstance().register(session);

            Map modes = environment.getPtyModes();
            modes.put(PtyMode.ECHO, 1);
            modes.remove(PtyMode.ICRNL);
            modes.put(PtyMode.ONOCR, 1);
            log.info("modes={}", modes);
            TtyFilterInputStream ttyIn = new TtyFilterInputStream(getInputStream(), modes);
            TtyFilterInputStream echo = new TtyFilterInputStream(System.in, modes);
            TtyFilterOutputStream ttyOut = new TtyFilterOutputStream(getOutputStream(), echo, modes);

            IOHandler ioHandler = new IOHandler(session, ttyIn, ttyOut);
            session.setIoHandler(ioHandler);
            ioHandler.sendMessage(room.getDisplay("text/plain"));

            // This is just plain old Java threads.  There's probably a much more efficient way to do
            // this, but it's nice to have something so simple

            //new Thread(ioHandler).start();
            ioHandler.run();

        }
    }
}
