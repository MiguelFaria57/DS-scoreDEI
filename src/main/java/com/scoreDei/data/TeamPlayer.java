package com.scoreDei.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@IdClass(TeamPlayerId.class)
public class TeamPlayer implements Serializable {
    @Id
    private int team_id;
    @Id
    private int player_id;

    public TeamPlayer() {
    }

    public TeamPlayer(int team_id, int player_id) {
        this.team_id = team_id;
        this.player_id = player_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
