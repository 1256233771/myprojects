package cins.art.numproduct.controller;

import cins.art.numproduct.DTO.UserRegisterForm;
import cins.art.numproduct.Util.EmailSend;
import cins.art.numproduct.Util.MyUtil;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.exception.MyException;
import cins.art.numproduct.service.RpcService;
import cins.art.numproduct.service.UserService;
import cins.art.numproduct.service.redis.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/unAuthentication")
@CrossOrigin
public class AnonController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private RpcService rpcService;



    @ApiOperation("注册用户接口")
    @PostMapping("/register")
    public Object register(@Valid UserRegisterForm userRegisterForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new MyException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        String key = redisService.getKey(userRegisterForm.getEmailAddress());
        log.info("key:{},userRegisterForm.getCode:{}",key,userRegisterForm.getCode());
        if(userRegisterForm.getCode().equals(key)){
            User user = Convert.convertUser(userRegisterForm);
            user.setRole("user");//TODO,默认为user
            //TODO 创建账户需要写入区块链
            try {
                user.setUserAddress(rpcService.createUser(user.getPassword()).toString());
            } catch (Throwable throwable) {
                return ResultVOUtil.error(ResultEnum.CREATE_USER_ADDRESS_FAIL);
            }
            Object userResult;
            try {
                userResult = userService.addUser(user);
            }catch (MyException e){
                return ResultVOUtil.error(ResultEnum.EMAIL_REGISTED);
            }
            return ResultVOUtil.success(userResult);
        }
        return ResultVOUtil.error(0,"验证码错误");
    }

    @GetMapping("/getLoginCode")
    public void getLoginCode(HttpSession session, HttpServletResponse resp){
        // 验证码图片的宽度。
        int width = 70;

        // 验证码图片的高度。
        int height = 30;

        // 验证码字符个数
        int codeCount = 4;

        int x = 0;

        // 字体高度
        int fontHeight;

        int codeY;

        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        x = width / (codeCount + 1);
        fontHeight = height - 2;
        codeY = height - 4;
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);

        // 画边框。
//        g.setColor(Color.BLACK);
//        g.drawRect(0, 0, width - 1, height - 1);

        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 1; i++) {
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x2, y2, x + xl, y2 + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();

        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        session.setAttribute("validateCode", randomCode.toString());
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 发送邮箱验证码
     * @return
     */
    @ApiOperation("发送邮箱验证码接口")
    @PostMapping("/sendCode")
    public Object sendCode(String emailAddress){
        String code = MyUtil.getRandomStr();
        try {
            EmailSend.sendMsgByMyEmail(code,emailAddress);
        } catch (Exception e) {
            log.error("邮件发送失败..");
            return ResultVOUtil.error(ResultEnum.SEND_EMAIL_FAIL);
        }
        redisService.setObject(emailAddress,code,4*60);//验证码时间设置为4分钟有效
        return ResultVOUtil.success();
    }

//    public static void main(String[] args) {
//        Md5Hash md5Hash = new Md5Hash("999***","1256233771@qq.com");
//        System.out.println(md5Hash.toString());
//    }
}
