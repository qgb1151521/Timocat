import java.io.*;
import java.net.*;
import timocat.FileProc;

public class Server {

    public static String getPath(String line) {
        String path = "";
        String a = line.substring(4);
        int index = a.indexOf(" HTTP/");
        path = a.substring(0, index);
        return path;
    }

    public static void main(String args[]) throws IOException {
        int port = 8899;
        String www_path = "D:/timocat/www/";
        ServerSocket server = new ServerSocket(port);
        System.out.println("server listen...");

        while (true) {

            Socket socket = server.accept();
            System.out.println("connection comming");

            System.out.println("start to read");

            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            String line = is.readLine();
            String path = Server.getPath(line);

            System.out.println("Client request path:" + path);

            String html = "";

            if (path.equals("/")) {
                // html = "<h1>Welcome to my home page!</h1>";
                Reader reader = null;
                char[] tempchars = new char[1024];
                try {
                    int charread = 0;
                    reader = new InputStreamReader(new FileInputStream("./index.html"));

                    while ((charread = reader.read(tempchars)) != -1) {
                        html += String.valueOf(tempchars);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }

                // html = tempchars.toString();
                // System.out.println(tempchars.toString());
            } else if (path.equals("/shutdown")) {
                html = "<h1>Welcome to shutdown my computer!</h1>";
                Process p = Runtime.getRuntime().exec("shutdown -s -t 120");
            } else if (path.equals("/noshutdown")) {
                html = "<h1>Don\'t shutdown my computer!</h1>";
                Process p = Runtime.getRuntime().exec("shutdown -a");
            } else {
                FileProc fp = new FileProc();
                path = www_path + path;
                System.out.println("Check file " + path);
                if (fp.exist(path)) {
                    System.out.println("File exist~");
                    html = fp.getFile(path);
                }
            }

            String headers = "HTTP/1.1 200 OK\r\n" +
                             "Date: Sun, 25 Jan 2015 12:46:58 GMT\r\n" +
                             "Server: Timocat/2.2.22 (Win64) Java/8\r\n" +
                             "X-Powered-By: Java/8\r\n" +
                             "Content-Length: " + html.length() + "\r\n" +
                             "Keep-Alive: timeout=5, max=99\r\n" +
                             "Connection: Keep-Alive\r\n" +
                             "Content-Type: text/html; charset=utf-8\r\n\r\n";

            String data = headers + html;

            if (false) {
                break;
            }

            os.println(data);
            os.flush();

            socket.close();
            is.close();
            sin.close();
        }

        server.close();
    }
}
