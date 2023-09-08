package com.scoreDei.services;

import com.scoreDei.data.TeamPlayer;
import com.scoreDei.repositories.TeamPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerService {
    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    public int[] getPlayerTeams(int player_id) {
        return teamPlayerRepository.getPlayerTeams(player_id);
    }

    public void createTeamPlayer(int team_id, int player_id) {
        TeamPlayer tp = new TeamPlayer(team_id, player_id);
        teamPlayerRepository.save(tp);
    }

    public int[] getTeamPlayers(int team_id){return teamPlayerRepository.getTeamPlayers(team_id);}

    public void deleteTeamPlayerById(int player_id){
        teamPlayerRepository.deleteTeamPlayerByPlayerId(player_id);
    }
}
