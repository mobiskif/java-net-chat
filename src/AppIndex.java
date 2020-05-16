import java.io.*;
import java.net.Socket;

public class AppIndex {

    public AppIndex(Socket s, String s1) {
        try {

            File file = new File(s1);
            long contentLength = file.length();
            BufferedReader url_in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            Helper.transferHead(net_out, contentLength);
            Helper.transferThread(url_in, net_out, "from_index").start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
