package com.ifce.jedi.service;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoComplementarDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.repository.PreInscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PreInscricaoService {

    @Autowired
    PreInscricaoRepository preInscricaoRepository;

    public PreInscricao createRegistration(PreInscricaoDto preInscricaoDto) {

        if (findByEmail(preInscricaoDto.email()).isPresent()){
            throw new IllegalArgumentException("Já existe uma pre-inscricao com esse email.");
        }

        PreInscricao preInscricao = new PreInscricao(
                preInscricaoDto.completeName(),
                preInscricaoDto.email(),
                preInscricaoDto.cellphone()
        );

        // Geração do token

        String token = UUID.randomUUID().toString();
        preInscricao.setContinuationToken(token);
        preInscricao.setTokenExpiration(LocalDateTime.now().plusHours(24));

        return  preInscricaoRepository.save(preInscricao);
    }


    public void completeRegistration(String token, PreInscricaoComplementarDto  preInscricaoComplementarDto) {
        PreInscricao preInscricao = preInscricaoRepository.findByContinuationToken(token)
                .orElseThrow(()-> new IllegalArgumentException("Token inválido."));

        if (preInscricao.getTokenExpiration() == null || LocalDateTime.now().isAfter(preInscricao.getTokenExpiration())) {
            throw new IllegalArgumentException("Token expirado.");
        }

        try {
            preInscricao.setBirthDate(preInscricaoComplementarDto.getBirthDate());
            preInscricao.setMunicipality(preInscricaoComplementarDto.getMunicipality());
            preInscricao.setCpf(preInscricaoComplementarDto.getCpf());
            preInscricao.setRg(preInscricaoComplementarDto.getRg());
            preInscricao.setDocument(preInscricaoComplementarDto.getDocument().getBytes());
            preInscricao.setProofOfAdress(preInscricaoComplementarDto.getProofOfAdress().getBytes());

            preInscricaoRepository.save(preInscricao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arivos enviados.", e);
        }

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
