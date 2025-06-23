package com.ifce.jedi.service;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoComplementarDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.exception.custom.EmailAlreadyUsedException;
import com.ifce.jedi.exception.custom.TokenExpiredException;
import com.ifce.jedi.exception.custom.TokenNotFoundException;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.model.User.StatusPreInscricao;
import com.ifce.jedi.repository.PreInscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PreInscricaoService {

    @Autowired
    PreInscricaoRepository preInscricaoRepository;

    @Autowired
    LocalStorageService localStorageService;

    public PreInscricao createRegistration(PreInscricaoDto preInscricaoDto) {

        if (preInscricaoRepository.existsByEmail(preInscricaoDto.email())) {
            throw new EmailAlreadyUsedException("E-mail já cadastrado.");
        }

        PreInscricao preInscricao = new PreInscricao(
                preInscricaoDto.completeName(),
                preInscricaoDto.email(),
                preInscricaoDto.cellphone()
        );

        preInscricao.setStatus(StatusPreInscricao.INCOMPLETO);

        String token = UUID.randomUUID().toString();
        preInscricao.setContinuationToken(token);
        preInscricao.setTokenExpiration(LocalDateTime.now().plusHours(24));

        return preInscricaoRepository.save(preInscricao);
    }


    public void completeRegistration(String token, PreInscricaoComplementarDto dto) {
        PreInscricao preInscricao = validateTokenOrThrow(token);

        try {
            String nomeDoc = localStorageService.salvar(dto.document());
            String nomeComprovante = localStorageService.salvar(dto.proofOfAdress());

            preInscricao.setBirthDate(dto.birthDate());
            preInscricao.setMunicipality(dto.municipality());
            preInscricao.setCpf(dto.cpf());
            preInscricao.setRg(dto.rg());
            preInscricao.setDocumentUrl("/arquivos/" + nomeDoc);
            preInscricao.setProofOfAdressUrl("/arquivos/" + nomeComprovante);

            preInscricao.setStatus(StatusPreInscricao.COMPLETO);

            preInscricaoRepository.save(preInscricao);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivos localmente.", e);
        }
    }




    public PreInscricao validateTokenOrThrow(String token) {
        PreInscricao preInscricao = findByContinuationToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token inválido."));

        if (preInscricao.getTokenExpiration() == null || preInscricao.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expirado.");
        }

        return preInscricao;
    }

    public PreInscricao findById(UUID id) {
        return preInscricaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pre Inscrição não encontrada" ));
    }

    public Optional<PreInscricao> findByEmail(String email) {
        return preInscricaoRepository.findByEmail(email);
    }


    public Optional<PreInscricao> findByContinuationToken(String token) {
        return preInscricaoRepository.findByContinuationToken(token);
    }

}
