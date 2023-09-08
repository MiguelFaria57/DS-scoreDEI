package com.scoreDei.repositories;

import com.scoreDei.data.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p.name FROM Player p WHERE p.name=?1")
    public String findPlayerName(String playerName);

    @Query("SELECT p.player_id FROM Player p WHERE p.name=?1")
    public int findPlayerId(String playerName);

    @Query("SELECT p.name FROM Player p WHERE p.player_id=?1")
    public String findPlayerNameById(int id);

    @Query("SELECT p.player_id FROM Player p WHERE p.name=?1")
    public Optional<Integer> findOptionalId(String player_name);

    @Query("SELECT p FROM Player p WHERE p.player_id <> 0")
    public Player[] getAllPlayers();
}