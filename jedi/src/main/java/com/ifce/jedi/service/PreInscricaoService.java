package com.ifce.jedi.service;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoComplementarDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDeleteResponseDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.exception.custom.*;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.model.User.StatusPreInscricao;
import com.ifce.jedi.repository.PreInscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PreInscricaoService {

    @Autowired
    PreInscricaoRepository preInscricaoRepository;

    @Autowired
    LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    private static final Set<String> MUNICIPIOS_PERMITIDOS = Set.of(
            "Belém", "Ananindeua", "Castanhal", "Santa Izabel do Pará", "Marituba",
            "Benevides", "Vigia", "Portel", "Breves", "Abaetetuba", "Moju",
            "Cametá", "Barcarena", "Tailândia", "Igarapé-Miri", "Acará",
            "Tomé-Açu", "Baião", "Bragança", "Capanema", "Viseu", "Capitão Poço",
            "Curuçá", "São Miguel do Guamá", "Salinópolis", "Outros"

    );

    public PreInscricao createRegistration(PreInscricaoDto preInscricaoDto) {
        if (preInscricaoDto.acceptedTerms() == null || !preInscricaoDto.acceptedTerms()) {
            throw new BusinessException("É obrigatório aceitar os termos de uso.");
        }

        Optional<PreInscricao> existente = preInscricaoRepository.findByEmail(preInscricaoDto.email());

        if (existente.isPresent()) {
            if (existente.get().getStatus() == StatusPreInscricao.COMPLETO) {
                throw new EmailAlreadyUsedException("E-mail já cadastrado.");
            } else {
                preInscricaoRepository.delete(existente.get());
            }
        }

        String municipioInformado = preInscricaoDto.municipality();

        if (!MUNICIPIOS_PERMITIDOS.contains(municipioInformado)) {
            throw new BusinessException("Município inválido.");
        }

        PreInscricao preInscricao = new PreInscricao(
                preInscricaoDto.completeName(),
                preInscricaoDto.email(),
                preInscricaoDto.cellphone()
        );

        preInscricao.setMunicipality(municipioInformado);

        if ("Outros".equalsIgnoreCase(municipioInformado)) {
            if (preInscricaoDto.otherMunicipality() == null || preInscricaoDto.otherMunicipality().isBlank()) {
                throw new BusinessException("Informe o nome do município quando selecionar 'Outros'.");
            }
            preInscricao.setOtherMunicipality(preInscricaoDto.otherMunicipality());
        }

        preInscricao.setAcceptedTerms(preInscricaoDto.acceptedTerms());
        preInscricao.setStatus(StatusPreInscricao.INCOMPLETO);

        String token = UUID.randomUUID().toString();
        preInscricao.setContinuationToken(token);
        preInscricao.setTokenExpiration(LocalDateTime.now().plusDays(30));

        return preInscricaoRepository.save(preInscricao);
    }

    public void completeRegistration(String token, PreInscricaoComplementarDto dto) {
        PreInscricao preInscricao = validateTokenOrThrow(token);

        if (preInscricao.getStatus() == StatusPreInscricao.COMPLETO) {
            throw new RegistrationAlreadyMadeException("Inscrição já finalizada, não é possível completar novamente.");
        }

        try {
            String nomeDoc = localStorageService.salvar(dto.document());
            String nomeComprovante = localStorageService.salvar(dto.proofOfAdress());

            preInscricao.setBirthDate(dto.birthDate());
            preInscricao.setCpf(dto.cpf());
            preInscricao.setRg(dto.rg());
            var linkNomeDoc = baseUrl + "/sensiveis/" + nomeDoc;
            var linkNomeComprovante = baseUrl + "/sensiveis/" + nomeComprovante;
            var linkSanitizadoDoc = linkNomeDoc.replaceAll("\\s+", "_");
            var linkSanitizadoComprovante = linkNomeComprovante.replaceAll("\\s+", "_");
            preInscricao.setDocumentUrl(linkSanitizadoDoc);
            preInscricao.setProofOfAdressUrl(linkSanitizadoComprovante);

            preInscricao.setStatus(StatusPreInscricao.COMPLETO);

            preInscricaoRepository.save(preInscricao);

        } catch (IOException e) {
            throw new UploadException("Erro ao fazer uploads.", e);
        }
    }


    public PreInscricaoDeleteResponseDto softDeletePreInscricao(UUID id) {
        PreInscricao preInscricao = preInscricaoRepository.findById(id)
                .orElseThrow(() -> new PreInscricaoNotFoundException(
                        "Pré-inscrição não encontrada com ID: " + id
                ));

        preInscricao.setAtivo(false);
        preInscricaoRepository.save(preInscricao);

        return PreInscricaoDeleteResponseDto.from(preInscricao.getId());
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
