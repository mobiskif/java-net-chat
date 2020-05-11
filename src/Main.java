import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket cs;
        try {
            if (args.length==0) cs = new Socket("localhost", 1234);
            else cs = new ServerSocket(1234).accept();
            new SocketHelper(cs);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
