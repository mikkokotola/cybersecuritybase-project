package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    private CustomUserDetailsService cudservice;

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Authentication authentication) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, Model model) {
        signupRepository.save(new Signup(name, address));
        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("registrations", signupRepository.findAll());
        return "done";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdmin(Authentication authentication, Model model) {
        
//        if (!authentication.getName().equals("admin")) {
//            return "redirect:/form";
//        }
        
        model.addAttribute("registrations", signupRepository.findAll());
        return "admin";

    }

    @RequestMapping(value = "/goto", method = RequestMethod.GET)
    public String goTo(@RequestParam String url) {
        return "redirect:" + url;
    }

}
