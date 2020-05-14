import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {
    boolean stop =false; //нужно для уведомления второго потока, при остановке первого
    final Socket socket;

    public Chat(Socket s) {
        socket = s;
        try {
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));

            transferThread(net_in, std_out, "from_net").start();
            transferThread(std_in, net_out, "to_net").start();

        } catch (IOException e) { e.printStackTrace();}
    }


    private Thread transferThread(BufferedReader in, PrintWriter out, String msg) {
        return new Thread(() -> {
            String readLine;
            try {
                while ((readLine = in.readLine()) != null && !stop) out.println("\t" + readLine);
                socket.close();
            }
            catch (IOException ignored) { }
            stop=true;
            System.out.println(msg +" поток остановлен ");
        });
    }

    public static void main(String[] args) {
        try {
            if (args.length==0) new Chat(new ServerSocket(1234).accept());
            else new Chat(new Socket(args[0], 1234));
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
