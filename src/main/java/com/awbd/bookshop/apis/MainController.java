package com.awbd.bookshop.apis;

import com.awbd.bookshop.services.user.IUserService;
import com.awbd.bookshop.services.user.UserService;
import jakarta.jws.soap.SOAPBinding;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class MainController {
    IUserService userService;
    public MainController(IUserService userService){
        this.userService=userService;
    }
//    @RequestMapping({"","/","/home"})
//    public ModelAndView getHome(){
//        return new ModelAndView("main");
//    }
    @RequestMapping({"","/","/home"})
    public ModelAndView getHome(Model model){
      //  int userId = getCurrentUserId();
        //model.addAttribute("userId", userId);
        return new ModelAndView("main");
    }
//    @GetMapping("/lala")
//    public String home(Model model) {
//        int userId = getCurrentUserId();
//        model.addAttribute("userId", userId);
//        return "home";
//    }

    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return userService.getId(authentication.getName());
        }
        return 0;
    }
    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }

//    @RequestMapping({"/category"})
//    public ModelAndView getCategoties(){
//
//        return new ModelAndView("categoryList");
//    }
//    @RequestMapping({"/category/form"})
//    public ModelAndView getCategotiesForm(){
//
//        return new ModelAndView("categoryForm");
//    }
}