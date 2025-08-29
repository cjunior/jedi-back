package com.ifce.jedi.service;


import com.ifce.jedi.dto.Ciclo.CicloRequestDto;
import com.ifce.jedi.dto.Ciclo.CicloResponseDto;
import com.ifce.jedi.dto.Ciclo.MunicipiosAtivosResponseDto;
import com.ifce.jedi.model.Ciclo.Ciclo;
import com.ifce.jedi.repository.CicloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    public CicloResponseDto criar(CicloRequestDto dto) {
        Ciclo ciclo = new Ciclo();
        ciclo.setNomeCiclo(dto.nome());
        ciclo.setDataInicio(dto.dataInicio());
        ciclo.setDataFim(dto.dataFim());
        ciclo.setMunicipios(dto.municipios());

        return CicloResponseDto.fromEntity(cicloRepository.save(ciclo));
    }

    public CicloResponseDto editar(UUID id, CicloRequestDto dto) {
        Ciclo ciclo = cicloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciclo não encontrado"));

        ciclo.setNomeCiclo(dto.nome());
        ciclo.setDataInicio(dto.dataInicio());
        ciclo.setDataFim(dto.dataFim());
        ciclo.setMunicipios(dto.municipios());

        return CicloResponseDto.fromEntity(cicloRepository.save(ciclo));
    }

    public void deletar(UUID id) {
        cicloRepository.deleteById(id);
    }

    public List<CicloResponseDto> listarTodos() {
        return cicloRepository.findAll().stream()
                .map(CicloResponseDto::fromEntity)
                .toList();
    }

    public Optional<CicloResponseDto> getCicloAtivo(LocalDateTime agora) {
        return cicloRepository.findByDataInicioBeforeAndDataFimAfter(agora, agora)
                .stream()
                .findFirst()
                .map(CicloResponseDto::fromEntity);
    }


    public MunicipiosAtivosResponseDto getMunicipiosCicloAtualComData() {
        LocalDateTime agora = LocalDateTime.now();

        Set<String> municipios = cicloRepository.findByDataInicioBeforeAndDataFimAfter(agora, agora)
                .stream()
                .flatMap(c -> c.getMunicipios().stream())
                .collect(Collectors.toSet());

        return new MunicipiosAtivosResponseDto(agora, municipios);
    }

}
