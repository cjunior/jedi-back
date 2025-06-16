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
    public ResponseEntity<TeamItemResponseDto> addMember(
            @RequestPart("file") MultipartFile file) throws IOException {

        TeamItemDto dto = new TeamItemDto();
        return ResponseEntity.ok(teamService.addMember(file, dto));
    }

    @PutMapping(value = "/member/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamItemResponseDto> updateMember(
            @PathVariable Long memberId,
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {

        TeamItemDto dto = new TeamItemDto();
        return ResponseEntity.ok(teamService.updateMember(memberId, file, dto));
    }

    @DeleteMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamResponseDto> deleteMember(@PathVariable Long memberId) throws IOException {
        return ResponseEntity.ok(teamService.deleteMember(memberId));
    }
}
