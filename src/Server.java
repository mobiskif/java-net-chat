import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    @Override
    public void run() {
        System.out.println(getClass().getSimpleName());
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true) {
                Socket socket = serverSocket.accept();
                //new AppIndex(socket, "src/index.html");
                new AppURL(socket, "https://mobiskif.github.io/hh_JAVA/");
                new AppChat(socket);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }

}
