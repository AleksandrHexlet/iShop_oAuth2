package com.authentication.oAuth_2.controller;

import com.authentication.oAuth_2.helper.ClientRegisterData;
import com.authentication.oAuth_2.helper.ResponseException;
import com.authentication.oAuth_2.helper.entity.ClientLoginData;
import com.authentication.oAuth_2.service.RegisteredClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
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

    @GetMapping("/client/registration")
    public String clientRegistrationGet(ClientRegisterData clientRegisterData) {
        return "ClientRegisterHTML";
    }


    @PostMapping("/client/registration")
    public String clientRegistration(@Valid ClientRegisterData clientRegisterData,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "ClientRegisterHTML";
        System.out.println("AFTER clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        try {
            RegisteredClient registeredClient = registeredClientService.clientRegistration(clientRegisterData);
            System.out.println("clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                    + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
            model.addAttribute("clientId", registeredClient.getClientId());
            model.addAttribute("clientSecret", registeredClient.getClientSecret());
            model.addAttribute("clientSecretExpiresAt", registeredClient.getClientSecretExpiresAt());
            return "redirect:/oauth/client/authorization";
//            return "redirect://client/registration/success";
//            return "ClientRegisterHTMLSuccess";
        } catch (ResponseException responseException) {
            System.out.println("clientRegistration error ===  " + responseException.getMessage());
//            bindingResult.addError(new ObjectError("ErrorText", responseException.getMessage()));
            model.addAttribute("ErrorText", responseException.getMessage());
            return "ClientRegisterHTML";
        }

    }

    @GetMapping("/client/authorization")
    public String getClientAuthorizationForm() {
        return "ClientAuthorizationFormHTML";
    }

    @GetMapping("/client/authorization/success")
    public String getClientRegistrationFormSuccess() {
        return "ClientAuthorizationSuccessHTML";
    }

    @GetMapping("/trader/authorization")
    public String getTraderAuthorizationForm() {
        return "TraderLoginFormHTML";
    }


}
