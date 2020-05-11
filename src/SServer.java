import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SServer implements Runnable {
    @Override
    public void run() {
        System.out.println(getClass().getName()+" run()");
        try {
            Socket cs = new ServerSocket(1234).accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
