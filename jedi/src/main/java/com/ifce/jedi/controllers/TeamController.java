package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Team.*;
import com.ifce.jedi.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/get")
    public ResponseEntity<TeamResponseDto> get() {
        TeamResponseDto team = teamService.getTeam();
        return team == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(team);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamResponseDto> update(@RequestBody TeamUpdateDto dto) {
        return ResponseEntity.ok(teamService.updateTeam(dto));
    }

    @PostMapping(value = "/members/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamResponseDto> addMember(
            @RequestPart("file") MultipartFile[] files) throws IOException {

        TeamItemDto dto = new TeamItemDto();
        return ResponseEntity.ok(teamService.addMember(files, dto));
    }

    @PutMapping(value = "/member/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMember(
            @PathVariable List<Long> memberId,
            @RequestPart(name = "file", required = false) MultipartFile[] files) throws IOException {

        if (memberId.size() != files.length) {
            return ResponseEntity.badRequest().body("Número de IDs e arquivos não correspondem.");
        }
        TeamItemDto dto = new TeamItemDto();
        for (int i = 0; i < memberId.size(); i++){
            teamService.updateMember(memberId.get(i), files[i], dto);
        }
        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }

    @DeleteMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMember(@PathVariable List<Long> memberId) throws IOException {
        for (int i = 0; i < memberId.size(); i++)
            teamService.deleteMember(memberId.get(i));
        return ResponseEntity.ok().body("Imagens excluídas com sucesso");
    }
}
