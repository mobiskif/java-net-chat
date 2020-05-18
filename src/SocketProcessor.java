import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
            /*
            //String content = getFile(path);
            //writeResponse(content, type);
            System.out.println(path + " " + type);
            Helper.from_to(path, os);

             */
            //writeResponse(path, "text/html");
            writeFile(path);
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

    private void writeResponse(long length, String type) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Accept-Ranges: bytes\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: " + type + "\r\n" +
                "Content-Length: " + length + "\r\n" +
                "Connection: keep-alive\r\n\r\n";
                //"Connection: closed\r\n\r\n";
        os.write(response.getBytes());
        os.flush();
    }

    private void writeFile(String fname) throws Throwable {
        String type = "text/html";
        String ext = fname.substring(fname.indexOf("."));
        switch (ext) {
            case ".html":
                type = "text/html; charset=utf-8";
                break;
            case ".css":
                type = "text/css; charset=utf-8";
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
            case ".mp4":
                type = "video/mp4";
                break;
        }

        File file = new File(fname);
        long contentLength = file.length();

        String result = fname + " " + contentLength + " " +type;
        System.out.println(result);

        writeResponse(contentLength,type);

        // Open the file and output streams
        FileInputStream in = new FileInputStream(file);
        OutputStream out = s.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        in.close();
        out.close();
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
        //System.out.println(path);
        return path;
    }
}