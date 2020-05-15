import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AppChat {

    public AppChat(Socket s) {
        try {
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            transferThread(net_in, std_out, "from_chat").start();

            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            transferThread(std_in, net_out, "to_chat").start();

        } catch (IOException e) { e.printStackTrace();}
    }

    public static Thread transferThread(BufferedReader in, PrintWriter out, String msg) {
        String header = "" +
                "HTTP/1.1 200 OK\n" +
                "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                "Server: Apache/2.2.14 (Win32)\n" +
                "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                "Content-Length: 127\n" +
                "Content-Type: text/html\n" +
                "Connection: Closed\n";
        out.println(header);
        return new Thread(() -> {
            String readLine = "";
            try {
                while ((readLine = in.readLine()) != null) out.println(readLine);
                in.close();
            }
            catch (IOException ignored) { }
            System.out.println(msg +" finished");
        });
    }

    public static void main(String[] args) {
        try {
            if (args.length==0) new AppChat(new ServerSocket(1234).accept());
            else new AppChat(new Socket(args[0], 1234));
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
