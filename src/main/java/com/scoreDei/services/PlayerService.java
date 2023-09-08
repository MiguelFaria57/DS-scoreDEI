package com.scoreDei.services;

import com.scoreDei.data.Player;
import com.scoreDei.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Integer> findOptionalID(String player_name) {
        return playerRepository.findOptionalId(player_name);
    }

    public String findPlayerName(String playerName) {
        return playerRepository.findPlayerName(playerName);
    }

    public int findPlayerId(String playerName) {
        return playerRepository.findPlayerId(playerName);
    }

    public String findPlayerNameById(int id){return playerRepository.findPlayerNameById(id);}

    public void createPlayer(String name, String position, Date birth_date) {
        Player p = new Player(name, position, birth_date);
        playerRepository.save(p);
    }

    public boolean updatePlayer(int player_id, String newName, String newPosition, Date newBirthDate) {
        Optional<Player> op = getPlayer(player_id);
        if (op.isPresent()) {
            Player p = op.get();
            if (!newName.equals(""))
                p.setName(newName);
            if (!newPosition.equals(""))
                p.setPosition(newPosition);
            p.setBirth_date(newBirthDate);
            playerRepository.save(p);
            return true;
        }
        return false;
    }

    public Player[] getAllPlayers(){return playerRepository.getAllPlayers();}

    public Optional<Player> getPlayer(int player_id) {
        return playerRepository.findById(player_id);
    }
}
