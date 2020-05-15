import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class AppURL {

    public AppURL(Socket s, String s1) {
        try {
            URL url = new URL(s1);
            BufferedReader url_in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            AppChat.transferThread(url_in, net_out, "from_url").start();

        } catch (IOException e) { e.printStackTrace();}
    }
}
