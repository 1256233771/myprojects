package cins.art.numproduct.controller;

import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.jwt.JWTUtil;
import cins.art.numproduct.ViewObject.ResultVO;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@CrossOrigin
public class WebController {
    @Autowired
    private UserService userService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        User user = userService.findUserByEmail(username);
        if (user==null){
            return ResultVOUtil.error(ResultEnum.NOT_EXIST);
        }
        Md5Hash md5Hash = new Md5Hash(password,username);
        if (user.getPassword().equals(md5Hash.toString())) {
            return ResultVOUtil.success(new ResultVO<>(200, "Login success", JWTUtil.sign(username, user.getPassword())));
        } else {
            return ResultVOUtil.error(ResultEnum.PASSWORD_WRONG);
        }
    }

    //判断当前用户是否登录
    @ApiOperation("得到当前访问的身份信息")
    @PostMapping("/article")
    public ResultVO article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return ResultVOUtil.success(subject.getPrincipal());
        } else {
            return new ResultVO(200, "当前用户是游客", null);
        }
    }

    @ApiOperation("获取当前用户的address信息")
    @PostMapping("/getUserAddress")
    @RequiresAuthentication
    public Object getUserAddress(Principal principal){
        User user = userService.getCurrentUser(principal);
        String userAddress = user.getUserAddress();
        return new ResultVO(200,"请求userAddress成功",userAddress);
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResultVO requireAuth() {
        return new ResultVO(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResultVO requireRole() {
        return new ResultVO(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResultVO requirePermission() {
        return new ResultVO(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultVO unauthorized() {
        return new ResultVO(401, "Unauthorized", null);
    }
}
