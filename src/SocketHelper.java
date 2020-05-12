import java.io.*;
import java.net.Socket;

public class SocketHelper {
    boolean try_stop=false;
    Socket socket;

    public SocketHelper(Socket s) {
        socket = s;
        try {
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));

            from_to(net_in, std_out, "net_con");
            from_to(std_in, net_out, "con_net");

        } catch (IOException e) { e.printStackTrace();}
    }


    private void from_to(BufferedReader in, PrintWriter out, String msg) {
        new Thread(() -> {
            String readLine;
            try {
                readLine = in.readLine();
                while (!try_stop) {
                    out.println("\t " + readLine);
                    if (readLine == null) try_stop=true;
                    else if (readLine.startsWith(".exit") || readLine.contains("null")  ) try_stop=true;
                    else readLine = in.readLine();
                }
                socket.close();
            } catch (IOException e) { }
            System.out.println(msg +" поток остановлен ");
        }).start();
    }

}
