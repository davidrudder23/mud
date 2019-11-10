package org.noses.mud.simple.input;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.command.CommandTable;
import org.noses.mud.simple.session.Session;

import java.io.*;

@Slf4j
public class IOHandler {
    BufferedReader in;
    PrintWriter out;
    Session session;

    public IOHandler(Session session, InputStream in, OutputStream out) {
        this.session = session;
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(new OutputStreamWriter(out));
    }

    public void run() throws IOException {
        while (true) {
            String line = in.readLine();
            CommandTable.getInstance().handleLine(this, session, line);
            out.println(session.getRoom().getDisplay("text/plain"));
        }
    }

    public void sendMessage(String message) {
        log.info (message);
        out.println(message);
        out.flush();
    }

    public void quit() throws IOException {
        in.close();
        out.close();
    }
}
