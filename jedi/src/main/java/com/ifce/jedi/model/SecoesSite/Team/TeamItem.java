package com.ifce.jedi.model.SecoesSite.Team;

import com.ifce.jedi.model.SecoesSite.BaseSectionItem;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "team_items")
public class TeamItem extends BaseSectionItem {
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamItem() {
    }

    public TeamItem(Team team) {
        this.team = team;
    }
}
