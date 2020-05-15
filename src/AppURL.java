import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class AppURL {

    public AppURL(Socket s, String s1) {
        try {
            URL url = new URL(s1);
            URLConnection con = url.openConnection();
            long contentLength = con.getContentLength();
            BufferedReader url_in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            Client.transferHead(net_out, contentLength);
            Client.transferThread(url_in, net_out, "from_url").start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
