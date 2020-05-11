import java.io.*;
import java.net.Socket;

public class SocketHelper {
    public SocketHelper(Socket s) {
        System.out.println(s);
        try {
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));

            from_to(net_in, std_out);
            from_to(std_in, net_out);
        }
        catch (IOException e) {e.printStackTrace();}
    }

    private void from_to(BufferedReader in, PrintWriter out) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String inputline = in.readLine();
                    while (!inputline.startsWith(".exit")) {
                        out.println(inputline);
                        inputline = in.readLine();
                    }
                    out.println(inputline);
                    System.out.println("=fin=");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
