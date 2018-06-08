package cins.art.numproduct.aspect;

import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.annoation.RequireAdminPermission;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Aspect
@Component
@Slf4j
public class PermissionAspect {

    @Autowired
    UserService userService;

    @Pointcut("@annotation(cins.art.numproduct.annoation.RequireAdminPermission)")
    public void permissionCut(){
        log.info("检验用户权限通过");
    }

    @ResponseBody
    @Around("permissionCut()&&@annotation(permission)")
    public Object judgePermission(ProceedingJoinPoint joinPoint, RequireAdminPermission permission) throws Throwable {
        log.info("校验用户的权限..");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        if (username==null){
            return ResultVOUtil.error(ResultEnum.AUTHENTICATION_REQUIRE);
        }
        User user = userService.findUserByEmail(username);
        log.info("访问用户:"+user.toString());
        if (user.getRole().equals(permission.value())){
            return joinPoint.proceed();
        }
        return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
    }

}
