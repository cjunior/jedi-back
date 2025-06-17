package com.ifce.jedi.controllers;


import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity register(@RequestBody @Valid RegisterDto registerDto){
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/pre-inscricoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PreInscricaoDadosDto>> getAllPreInscricoes() {
        return ResponseEntity.ok(userService.getAllPreInscricoes());
    }


}
