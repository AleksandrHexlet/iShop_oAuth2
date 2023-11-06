package com.authentication.oAuth_2.controller;

import com.authentication.oAuth_2.helper.ClientRegisterData;
import com.authentication.oAuth_2.helper.ResponseException;
import com.authentication.oAuth_2.service.RegisteredClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Controller
@RequestMapping("/oauth")
public class RegisteredClientController {
    private RegisteredClientService registeredClientService;

    @Autowired
    public RegisteredClientController(RegisteredClientService registeredClientService) {
        this.registeredClientService = registeredClientService;
    }

    @PostMapping("/client/registration")
    public String clientRegistration(@Valid ClientRegisterData clientRegisterData,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "ClientRegisterHTML";
        System.out.println("AFTER clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        try {
            registeredClientService.clientRegistration(clientRegisterData);
            System.out.println("clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                    + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        } catch (ResponseException responseException) {
            bindingResult.addError(new ObjectError("ErrorText", responseException.getMessage()));
//            model.addAttribute("ErrorText", responseException.getMessage());
            return "ClientRegisterHTML";
        }
        return "";
    }

    @GetMapping("/client/registration")
    public String getClientRegistrationForm(ClientRegisterData clientRegisterData) {
        return "ClientRegisterHTML";
    }

    @GetMapping("/trader/authorization")
    public String getTraderAuthorizationForm() {
        return "TraderLoginFormHTML";
    }


}
