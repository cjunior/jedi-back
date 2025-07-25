package com.ifce.jedi.service;

import com.ifce.jedi.dto.Team.*;
import com.ifce.jedi.model.SecoesSite.Team.Team;
import com.ifce.jedi.model.SecoesSite.Team.TeamItem;
import com.ifce.jedi.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;
    @Transactional
    public TeamResponseDto createTeam(TeamDto dto) {
        Team team = new Team();
        team.setTitle(dto.title());

        List<TeamItem> items = dto.items().stream().map(s -> {
            TeamItem item = new TeamItem();
            item.setImgUrl(s.imgUrl());
            item.setTeam(team);
            return item;
        }).toList();

        team.setItems(items);
        Team saved = teamRepository.save(team);

        return toResponse(saved);
    }

    @Transactional
    public TeamResponseDto updateTeam(TeamUpdateDto dto) {
        Team team = teamRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Team não encontrado."));

        team.setTitle(dto.title());
        Team updated = teamRepository.save(team);
        return toResponse(updated);
    }

    @Transactional
    public TeamItemResponseDto updateMember(Long itemId, MultipartFile file, TeamItemDto dto) throws IOException {
        Team team = teamRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Team não encontrado."));

        TeamItem item = team.getItems().stream()
                .filter(s -> s.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Membro não encontrado."));

        if (file != null && !file.isEmpty()) {
            if (item.getFileName() != null) {
                localStorageService.deletar(item.getFileName());
            }
            var uploadResult = localStorageService.salvar(file);
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImgUrl(linkSanitizado);
            item.setFileName(uploadResult);
        }

        Team updated = teamRepository.save(team);

        return new TeamItemResponseDto(
                item.getId(), item.getImgUrl());
    }

    @Transactional
    public TeamResponseDto getTeam() {
        return teamRepository.findAll().stream()
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public TeamResponseDto addMember(MultipartFile[] files, TeamItemDto dto) throws IOException {
        Team team = teamRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Team não encontrado."));

        for (MultipartFile file : files){
            TeamItem item = new TeamItem();
            var uploadResult = localStorageService.salvar(file);
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImgUrl(linkSanitizado);
            item.setFileName(uploadResult);
            item.setTeam(team);
            team.getItems().add(item);
        }



        teamRepository.save(team);

        return toResponse(team);
    }

    @Transactional
    public TeamResponseDto deleteMember(long id) throws IOException {
        Team team = teamRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team não encontrado."));

        TeamItem itemToRemove = team.getItems().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Membro não encontrado."));

        localStorageService.deletar(itemToRemove.getFileName());
        team.getItems().remove(itemToRemove);

        teamRepository.save(team);

        return toResponse(team);
    }

    private TeamResponseDto toResponse(Team team) {
        List<TeamItemResponseDto> members = team.getItems().stream().map(s ->
                new TeamItemResponseDto(s.getId(), s.getImgUrl())
        ).collect(Collectors.toList());

        return new TeamResponseDto(
                team.getId(),
                team.getTitle(),
                members
        );
    }
}
