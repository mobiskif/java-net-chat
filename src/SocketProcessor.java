import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }

    public String getFile(String fname) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(fname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public void run() {
        try {
            String path = readInputHeaders();
            String type = "text/html";
            String ext = path.substring(path.indexOf("."));
            switch (ext) {
                case ".html":
                    type = "text/html";
                    break;
                case ".css":
                    type = "text/css";
                    break;
                case ".ico":
                    type = "image/x-icon";
                    break;
                case ".jpg":
                case ".jpeg":
                    type = "image/jpeg";
                    break;
                case ".png":
                    type = "image/png";
                    break;
            }
            String content = getFile(path);
            
            writeResponse(content, type);
        } catch (Throwable t) {
            /*do nothing*/
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                /*do nothing*/
            }
        }
        //System.err.println("Client processing finished");
    }

    private void writeResponse(String s, String type) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: "+ type +"\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }

    private String readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        String[] arr = s.split(" ");
        /*
        while (true) {
            String s = br.readLine();
            System.out.println(s);
            if (s == null || s.trim().length() == 0) {
                break;
            }
        }
        */
        String path = arr[1].substring(1);
        if (path.equals("")) path = "index.html";
        System.out.println(path);
        return path;
    }
}