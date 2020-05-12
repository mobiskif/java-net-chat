import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHelper {
    boolean stop_request = false;

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
        new Thread(() -> {
            try {
                String readLine = in.readLine();
                while (!stop_request) {
                    out.println("\t "+readLine);
                    if (readLine.startsWith(".exit")) stop_request = true;
                    else readLine = in.readLine();
                }
                in.close();
                out.close();
            }
            catch (IOException e) {
                //e.printStackTrace();
            }
        }).start();
    }

}
