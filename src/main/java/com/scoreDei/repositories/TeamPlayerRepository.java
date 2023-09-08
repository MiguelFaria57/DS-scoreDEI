package com.scoreDei.repositories;

import com.scoreDei.data.TeamPlayer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TeamPlayerRepository extends CrudRepository<TeamPlayer, Integer> {
    @Query("SELECT tp.team_id FROM TeamPlayer tp WHERE tp.player_id=?1")
    public int[] getPlayerTeams(int player_id);

    @Query("SELECT tp.player_id FROM TeamPlayer tp WHERE tp.team_id=?1")
    public int[] getTeamPlayers(int team_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM TeamPlayer tp WHERE tp.player_id=?1")
    void deleteTeamPlayerByPlayerId(int player_id);
}

