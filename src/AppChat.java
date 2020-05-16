import java.io.*;
import java.net.Socket;

public class AppChat {

    public AppChat(Socket s) {
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            Helper.transferThread(net_in, std_out, "from_chat").start();

            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            Helper.transferThread(std_in, net_out, "to_chat").start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            new AppChat(new Socket(args[0], 1234));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
