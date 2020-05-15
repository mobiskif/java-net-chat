import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class AppIndex {

    public AppIndex(Socket s) {
        try {

            BufferedReader url_in = new BufferedReader(new InputStreamReader(new FileInputStream("src/index.html"), "UTF-8"));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            AppChat.transferThread(url_in, net_out, "from_index").start();

        } catch (IOException e) { e.printStackTrace();}
    }
}
