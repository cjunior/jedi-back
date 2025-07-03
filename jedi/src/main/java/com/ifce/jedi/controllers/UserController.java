package com.ifce.jedi.controllers;


import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.dto.User.UserResponseDto;
import com.ifce.jedi.dto.User.UserTableResponseDto;
import com.ifce.jedi.model.User.StatusPreInscricao;
import com.ifce.jedi.service.PdfService;
import com.ifce.jedi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PdfService pdfService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity register(@ModelAttribute @Valid RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/pre-inscricoes")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<PreInscricaoDadosDto>> getAllPreInscricoes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) StatusPreInscricao status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getAllPreInscricoes(nome, email, status, pageable));
    }


    @GetMapping("/relatorio/pre-inscricoes/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<byte[]> gerarRelatorioPDF(
            @RequestParam(required = false) StatusPreInscricao status
    ) {
        List<PreInscricaoDadosDto> dados = userService
                .getAllPreInscricoes(null, null, status, Pageable.unpaged())
                .getContent();

        byte[] pdf = pdfService.gerarRelatorioPreInscricoes(dados);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("relatorio_preinscricoes.pdf")
                .build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<UserTableResponseDto>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchTerm) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(userService.getPaginatedUsers(pageable, searchTerm));
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
