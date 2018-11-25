package cins.art.numproduct.Util;

import cins.art.numproduct.DTO.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Slf4j
public class MyUtil {
//    public static String uploadPicPath = "/home/hk/图片/test/";//图片默认上传的文件夹
    public static String uploadVideoPath = "/home/hk/视频/test/";//视频文件保存路径
//    public static String uploadPicPath = "/home/ubuntu/clf/numproduct/";//图片默认上传的文件夹
//    public static String uploadVideoPath = "/home/ubuntu/clf/numproduct/";//视频文件保存路径
    public static String uploadPicPath = "/usr/local/nginx/html/numproduct/";//图片默认上传的文件夹
//    public static String uploadVideoPath = "/home/cins/clf/videoFile";//视频文件保存路径
    public static String picUrl = "images/";//图片上传后保存的相对路径
//    public static String picUrl = "";//图片上传后保存的相对路径
    /**
     * 生成一个随机的六位字符
     * @return
     */
    public static String getRandomStr(){
        return RandomStringUtils.random(6,"1234567890");
    }

    /**
     * 生成唯一主键:
     * 时间+随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer num = random.nextInt(900000)+100000;//生成6为随机数
        return System.currentTimeMillis()+String.valueOf(num);
    }


    public static boolean uploadFile(MultipartFile file,String uploadPath){
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadPath);
            Files.write(path, bytes);
        } catch (IOException e) {
            return false;
        }
        return true;
    }



    public static UploadProperties getUploadPicPath(){
        UploadProperties uploadProperties = new UploadProperties();
        uploadProperties.setPictureUrl(picUrl+System.currentTimeMillis()+getRandomStr()+".jpg");
        uploadProperties.setUploadPath(MyUtil.uploadPicPath+uploadProperties.getPictureUrl());
        log.info("上传文件的路径:"+uploadProperties.getUploadPath()
                +"数据库的信息保存路径:"+uploadProperties.getPictureUrl());
        return uploadProperties;//指定上传的路径
    }
    public static String getUploadVideoPath(MultipartFile file){
        return MyUtil.uploadVideoPath+System.currentTimeMillis()+getRandomStr()+".mp4";
    }
}
