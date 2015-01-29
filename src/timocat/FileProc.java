package timocat;
import java.io.*;

public class FileProc {

    public static void main(String[] args) {
        FileProc f = new FileProc();
        String path = "E:/1.txt";
        if (f.exist(path)) {
            System.out.println(path + " exist");

            String s = f.getFile(path);
            System.out.println(s);
        } else {
            System.out.println(path + " not exist");
        }
    }

    public void test() {
        System.out.println("hello file");
    }

    /*
     * 读取文件内容
     */
    public String getFile(String path) {
        Reader reader = null;
        char[] buf = new char[1024];
        String content = "";
        try {
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(path));
            while ((charread = reader.read(buf)) != -1) {
                if ((charread == buf.length)
                        && (buf[buf.length - 1] != '\r')) {
                    System.out.print("buf:" + buf);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (buf[i] == '\r') {
                            continue;
                        } else {
                            content += buf[i];
                        }
                    }
                }

            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e1) {
                //
            }
            return content;
        }
    }

    /*
     * 判断文件是否存在
     */
    public Boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }
}