package org.noses.mud.simple.input;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.command.CommandTable;
import org.noses.mud.simple.session.Session;

import java.io.*;

@Slf4j
public class IOHandler implements Runnable {
    BufferedReader in;
    PrintWriter out;
    Session session;

    public IOHandler(Session session, InputStream in, OutputStream out) {
        this.session = session;
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(new OutputStreamWriter(out));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = in.readLine();
                CommandTable.getInstance().handleLine(this, session, line);
                out.println(session.getRoom().getDisplay("text/plain"));
            }
        } catch (IOException ioExc) {
            log.warn("Session dropped", ioExc);
        }
    }

    public void sendMessage(Session speaker, String message) {
        if (speaker == null) {
            sendMessage(message);
        } else {
            sendMessage(speaker.getName()+": "+message);
        }
    }

    public void sendMessage(String message) {
        log.info (message);
        out.println(message);
        out.println("\r\n");
        out.flush();
    }

    public void quit() throws IOException {
        in.close();
        out.close();
    }
}
