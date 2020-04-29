package com.find;

import org.junit.jupiter.api.Test;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FaceImageTest {

    @Test
    public void testThum() throws IOException {
        String url = "D:\\我的用户\\新建文件夹\\QQ图片20200313213824.jpg";
        byte[] bytes = null;
        try(InputStream in = new FileInputStream(url);) {
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        // 通过base64来转化图片
        String data = encoder.encode(bytes);

    }

    @Test
    public void testB() throws IOException {
        String url = "D:\\我的用户\\新建文件夹\\QQ图片20200313213824.jpg";
        byte[] bytes = null;
        try(InputStream in = new FileInputStream(url);) {
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        // 通过base64来转化图片
        String data = encoder.encode(bytes);
        System.out.println(data);
    }

    @Test
    public void testC(){

    }
}
