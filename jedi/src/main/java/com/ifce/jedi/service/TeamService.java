package com.ifce.jedi.service;

import com.ifce.jedi.dto.Team.*;
import com.ifce.jedi.model.SecoesSite.Team;
import com.ifce.jedi.model.SecoesSite.TeamItem;
import com.ifce.jedi.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CloudinaryService cloudinaryService;

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
            if (item.getCloudinaryPublicId() != null) {
                cloudinaryService.deleteImage(item.getCloudinaryPublicId());
            }
            var uploadResult = cloudinaryService.uploadImage(file);
            item.setImgUrl(uploadResult.get("url"));
            item.setCloudinaryPublicId(uploadResult.get("public_id"));
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
    public TeamItemResponseDto addMember(MultipartFile file, TeamItemDto dto) throws IOException {
        Team team = teamRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Team não encontrado."));

        TeamItem item = new TeamItem();

        var uploadResult = cloudinaryService.uploadImage(file);
        item.setImgUrl(uploadResult.get("url"));
        item.setCloudinaryPublicId(uploadResult.get("public_id"));
        item.setTeam(team);

        team.getItems().add(item);
        teamRepository.save(team);

        return new TeamItemResponseDto(
                item.getId(), item.getImgUrl());
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

        cloudinaryService.deleteImage(itemToRemove.getCloudinaryPublicId());
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
