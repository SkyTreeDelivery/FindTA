package com.find.Util.Utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ImageUtils {
    public static void genarateThumbnails(String originImagePath,int width,int height,String thumnailImagePath) throws IOException {
//        会按比例生成缩略图
        Thumbnails.of(originImagePath).size(width, height).toFile(thumnailImagePath);
    }
}
