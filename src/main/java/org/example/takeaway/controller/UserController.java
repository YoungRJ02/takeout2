package org.example.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.takeaway.common.R;
import org.example.takeaway.entity.User;
import org.example.takeaway.service.UserService;
import org.example.takeaway.utils.SMSUtils;
import org.example.takeaway.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession httpSession){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);
            SMSUtils.sendMessage("ruiji", "SMS_461860872", phone, code);
            httpSession.setAttribute(phone, code);
            return R.success("验证码发送成功");
        }

        return R.error("短信发送失败");
    }

    /**
     * 移动端登录
     * @param map
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        Object codeinsession = httpSession.getAttribute(phone);
        if (codeinsession != null && codeinsession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            httpSession.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        //清理Session中保存的当前用户登录的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }
}
