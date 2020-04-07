package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.User;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebUserService;
import com.lms.Lblog.spring.cloud.web.admin.feign.utility.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * Autor Lms
 * Time 2019/9/1/001
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private WebUserService userService;

    @GetMapping         //不加映射路径的话，进入了/admin映射后，就自动进入这个映射的方法
//    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(){
        return "/admin/login";       //自动跳转到登录页面
    }

    @PostMapping("/login")
    //get请求提交的参数需要和Controller方法中的入参名称一致
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes, Model model){       //@RequestParam注解是用于接收前台post携带的参数/请求数据？
        User user =userService.checkUser(username,password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "/admin/wellcomePage";
        } else {
            attributes.addFlashAttribute("message","用户名和密码错误了");
            return "redirect:/admin";  //为什么这儿重定向的地址是mapping映射？
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        System.out.println("logout");
        return "redirect:/admin";
    }

    //注册
    @PostMapping("/register")
    public String register(User user,Model model){
        Random random=new Random();
        int i=random.nextInt(11);//输出[0,11)之间的整数int
        user.setAvatar(i+".jpg");
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setCreateTime(new Date());
        String username=user.getUsername();
        System.out.println(username);
        User userB=userService.getUserByname(username);
        if(userB==null){
            User commit=userService.saveUser(user);
            if(commit!=null){
                model.addAttribute("message","注册成功！");
            }else{
                model.addAttribute("message","注册失败！请重试");
            }
        }else{
            model.addAttribute("message","用户名已存在！");
        }
        return "redirect:/";
    }
}
