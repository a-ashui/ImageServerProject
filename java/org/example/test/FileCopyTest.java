package org.example.test;

import java.io.*;

public class FileCopyTest {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("F:\\image\\IMG_3560.JPG");

        FileOutputStream fos = new FileOutputStream("F://1.JPG");

        byte[] bytes = new byte[1024];
        int len;
        while( (len = is.read(bytes)) != -1){
            fos.write(bytes,0,len);
        }

        is.close();
        fos.close();

    }
}
