package sun.dfs.pj.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class VedioUtils {
   static final List<String> video =
            Arrays.asList("mp4", "m4v", "wmv", "asf", "asx", "rm", "rmv", "bmpg", "mpeg", "mpe", "3gp", "mov", "avi", "dat", "mkv", "flv", "vob");
    //"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
    static final List<String> image =   Arrays.asList(
            "bmp","jpg","png","tif","gif","pcx","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","ai","raw","wmf","webp");
    static final List String = Arrays.asList("jpg", "jpeg", "png", "gif", "BMP", "WBMP");
    public static boolean  videoTypeContains(String type){
        return video.contains(type);
    }
    public static boolean  imageTypeContains(String type){
        return String.contains(type);
    }
    public static  BufferedImage getVideoThumbnail(InputStream in) {
        FFmpegFrameGrabber fFmpegFrameGrabber = null;
        fFmpegFrameGrabber = new FFmpegFrameGrabber(in);
        BufferedImage thumbnailImage =null;
        try {
            fFmpegFrameGrabber.start();
            // 截取的帧图片
            int i = 0;
            int length = fFmpegFrameGrabber.getLengthInFrames();
            int middleFrame = length / 2;
            Frame frame = null;
            while (i < length) {
                frame = fFmpegFrameGrabber.grabFrame();
                if ((i > middleFrame) && (frame.image != null)) {
                    break;
                }
                i++;
            }
            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();
            // 对截图进行等比例缩放(缩略图)
            int width = 480;
            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            fFmpegFrameGrabber.stop();
            return thumbnailImage;
        } catch (Exception e) {
            System.err.println("发生错误，{}" + e.getMessage());
        }
        return thumbnailImage;
    }
    public static  InputStream[] copyInputStream(InputStream inputStream,int size){
        InputStream[] inputStreams = new InputStream[size];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 打开两个新的输入流
        for (int i = 0; i < size; i++) {
            inputStreams[i]= new ByteArrayInputStream(outputStream.toByteArray());
        }
        return inputStreams;
    }

}
