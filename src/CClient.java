import java.io.IOException;
import java.net.Socket;

public class CClient implements Runnable {
    @Override
    public void run() {
        System.out.println(getClass().getName()+" run()");
        try {
            Socket cs = new Socket("localhost", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
