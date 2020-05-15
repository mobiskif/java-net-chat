import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.MatchResult;

public class AppChat {

    public AppChat(Socket s) {
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            Client.transferThread(net_in, std_out, "from_chat").start();

            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            Client.transferThread(std_in, net_out, "to_chat").start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
