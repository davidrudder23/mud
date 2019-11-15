package org.noses.mud.simple.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.hostbased.HostBasedAuthenticator;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ShellFactory;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

@Slf4j
public class SSHListener implements Runnable {
        int port;

        public SSHListener(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            log.info("Running on port {}", port);
            SshServer sshd = SshServer.setUpDefaultServer();
            sshd.setPort(port);
            
            //sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
            sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
            sshd.setHostBasedAuthenticator(new HostBasedAuthenticator() {
                @Override
                public boolean authenticate(ServerSession serverSession, String s, PublicKey publicKey, String s1, String s2, List<X509Certificate> list) {
                    return true;
                }
            });

            sshd.setShellFactory(new SSHStreamFactory());

            try {
                sshd.start();
            } catch (IOException e) {
                log.error("Could not create ssh server", e);
            }
        }
}
