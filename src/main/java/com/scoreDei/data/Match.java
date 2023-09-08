package com.scoreDei.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@Entity
@XmlRootElement
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int match_id;
    private String location;
    private Date match_date;
    private int team_a_id;
    private int team_b_id;

    public Match() {
    }

    public Match(int match_id, String location, Date match_date, int team_a_id, int team_b_id) {
        this.match_id = match_id;
        this.location = location;
        this.match_date = match_date;
        this.team_a_id = team_a_id;
        this.team_b_id = team_b_id;
    }

    public Match(String location, Date match_date, int team_a_id, int team_b_id) {
        this.location = location;
        this.match_date = match_date;
        this.team_a_id = team_a_id;
        this.team_b_id = team_b_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getMatch_date() {
        return match_date;
    }

    public void setMatch_date(Date match_date) {
        this.match_date = match_date;
    }

    public int getTeam_a_id() {
        return team_a_id;
    }

    public void setTeam_a_id(int team_a_id) {
        this.team_a_id = team_a_id;
    }

    public int getTeam_b_id() {
        return team_b_id;
    }

    public void setTeam_b_id(int team_b_id) {
        this.team_b_id = team_b_id;
    }
}
