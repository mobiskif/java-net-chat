import java.io.*;
import java.net.Socket;

public class SocketHelper {
    boolean try_stop=false;

    public SocketHelper(Socket s) {
        try {
            PrintWriter net_out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader net_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter std_out = new PrintWriter(System.out, true);
            BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));

            from_to(std_in, net_out);
            from_to(net_in, std_out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void from_to(BufferedReader in, PrintWriter out) {
        new Thread(() -> {
            //try {
            String readLine = "";
            try {
                readLine = in.readLine();
                while (!try_stop) {
                    out.println("\t " + readLine);
                    if (readLine == null) {
                        System.out.println("======");
                        try_stop=true;
                    }
                    else if (readLine.startsWith(".exit")) try_stop=true;
                    else {
                        try {
                            readLine = in.readLine();
                        } catch (IOException e) {
                            System.err.println("Второе чтение: " + e);
                            try_stop=true;
                        }
                    }
                }
                out.close();
                in.close();
            } catch (IOException e) {
                System.err.println("Первое чтение: " + e);
            }
            //out.close();
            //} catch (IOException e) { e.printStackTrace();}
        }).start();
    }

}
