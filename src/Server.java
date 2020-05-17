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
                //new AppIndex(socket, "index.html");
                //new AppURL(socket, "https://mobiskif.github.io/hh_JAVA/");
                //new AppChat(socket);
                new Thread(new SocketProcessor(socket)).start();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }

}
