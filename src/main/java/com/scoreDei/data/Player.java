package com.scoreDei.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@Entity
@XmlRootElement
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int player_id;
    private String name;
    private String position;
    private Date birth_date;

    public Player() {
    }

    public Player(int player_id, String name, String position, Date birth_date) {
        this.player_id = player_id;
        this.name = name;
        this.position = position;
        this.birth_date = birth_date;
    }

    public Player(String name, String position, Date birth_date) {
        this.name = name;
        this.position = position;
        this.birth_date = birth_date;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }
}