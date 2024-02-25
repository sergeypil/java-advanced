package net.serg.secretproviders.controller;

import lombok.RequiredArgsConstructor;
import net.serg.secretproviders.dto.SecretRequestDto;
import net.serg.secretproviders.service.SecretService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class SecretController {

    private final SecretService secretService;

    @GetMapping("/secrets")
    public String index(Model model) {
        return "secret";
    }
    
    @PostMapping("/secrets")
    public ResponseEntity<URI> submitSecret(@ModelAttribute("Secret") SecretRequestDto secretRequestDto) {
        String uniqueId = secretService.saveSecret(secretRequestDto);
        URI secretLinkUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/secrets/{id}")
            .buildAndExpand(uniqueId)
            .toUri();
        return new ResponseEntity<>(secretLinkUri, HttpStatus.CREATED);
    }

    @GetMapping("/secrets/{uniqueId}")
    public ResponseEntity<String> getSecret(@PathVariable("uniqueId") String id) {
        return ResponseEntity.ok(secretService.getSecretByUniqueId(id));
    }
}