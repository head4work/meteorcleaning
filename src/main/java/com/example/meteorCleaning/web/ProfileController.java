package com.example.meteorCleaning.web;

import com.example.meteorCleaning.dto.PasswordRecoveryTo;
import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/forgot")
public class ProfileController extends AbstractUserController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/{token}")
    public String forgotPassword(@PathVariable String token, Model model) {
        ForgottenPasswordToken passwordToken = tokenService.get(token);
        model.addAttribute("valid", tokenService.validateTokenExpired(passwordToken));
        model.addAttribute("passwordRecoveryTo", new PasswordRecoveryTo(passwordToken));
        return "forgot";
    }

    @GetMapping()
    public String forgotPassword(@RequestParam String success) {
        if (success.equals("Password been successfully changed")) ;
        {
            return "forgot";
        }
    }

    @PostMapping()
    public String updatePasswordWithToken(@Valid PasswordRecoveryTo passwordRecoveryTo, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        boolean valid = tokenService.validateTokenExpired(passwordRecoveryTo.getToken());
        model.addAttribute("valid", valid);
        if (!valid) {
            ObjectError error = new ObjectError("global", "Token has expired");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "forgot";
        }
        super.updateFromForgot(passwordRecoveryTo);
        redirectAttributes.addAttribute("success", "Password been successfully changed");
        return "redirect:/forgot";
    }
}
