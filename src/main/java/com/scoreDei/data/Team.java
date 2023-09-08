package com.scoreDei.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int team_id;
    private String image_url;
    private String name;
    private int games;
    private int wins;
    private int losses;
    private int draws;

    public Team(){

    }

    public Team(int team_id, String image_url, String name, int games, int wins, int losses, int draws) {
        this.team_id = team_id;
        this.image_url = image_url;
        this.name = name;
        this.games = games;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public Team(String image_url, String name, int games, int wins, int losses, int draws) {
        this.image_url = image_url;
        this.name = name;
        this.games = games;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
}
