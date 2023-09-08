package com.scoreDei.data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Entity
@XmlRootElement
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int event_id;
    private boolean is_valid;
    private String type;
    private Timestamp event_date;
    private int team_id;
    private int player_id;
    private int match_id;

    public Event() {
    }

    public Event(int event_id, boolean is_valid, String type, Timestamp event_date, int team_id, int player_id, int match_id) {
        this.event_id = event_id;
        this.is_valid = is_valid;
        this.type = type;
        this.event_date = event_date;
        this.team_id = team_id;
        this.player_id = player_id;
        this.match_id = match_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Timestamp event_date) {
        this.event_date = event_date;
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

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }
}
