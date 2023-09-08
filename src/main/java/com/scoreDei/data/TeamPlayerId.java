package com.scoreDei.data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TeamPlayerId implements Serializable {
    private int team_id;
    private int player_id;

    public TeamPlayerId() {

    }

    public TeamPlayerId(int team_id, int player_id) {
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
