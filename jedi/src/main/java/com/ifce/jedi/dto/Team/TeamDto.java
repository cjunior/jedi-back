package com.ifce.jedi.dto.Team;


import java.util.List;

public record TeamDto(String title, List<TeamItemUrlDto> items) {
}
