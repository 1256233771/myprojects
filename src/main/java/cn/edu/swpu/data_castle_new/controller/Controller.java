package cn.edu.swpu.data_castle_new.controller;

import cn.edu.swpu.data_castle_new.util.CsvUtil;
import cn.edu.swpu.data_castle_new.util.LocalExecute;
import cn.edu.swpu.data_castle_new.util.SshUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
public class Controller {


    /**
     * 服务器位置
     **/
    @PostMapping("/up")
    public Object singleFileUpload(

            HttpServletRequest request,
            @RequestParam("file") MultipartFile file) throws Exception {

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");//你的编码格式
        }
        /**判断是否为空**/
        if (file.isEmpty()) {
            return "文件为空";
        }
        String filePath = CsvUtil.UPLOADED_LOCAL_FOLDER + file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            System.out.println(file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            System.out.println("上传本地失败！");
            e.printStackTrace();
            return "上传本地文件失败";
        }
        if(!CsvUtil.read(filePath,file.getOriginalFilename())){
            return "失败,文件不满足要求..";
        }

        System.out.println("上传本地成功！");
        try {

            if (!SshUtil.putFile(filePath)){
                return "返回对应上传失败json";
            }
            log.info("ssh上传文件成功,删除本地临时文件...");
            LocalExecute.removeFile(filePath);
        }catch (Exception e){
            log.error("ssh上传文件失败,或删除文件失败");
            return "ssh上传失败..";
        }

        return "ssh上传成功";
    }
}
