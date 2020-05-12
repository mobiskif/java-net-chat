import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length==0) new SocketHelper(new ServerSocket(1234).accept());
            else new SocketHelper (new Socket(args[0], 1234));
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
