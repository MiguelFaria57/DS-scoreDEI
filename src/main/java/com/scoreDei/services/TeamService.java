package com.scoreDei.services;

import com.scoreDei.data.Team;
import com.scoreDei.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public String findTeamNameById(int team_id) {
        return teamRepository.findTeamNameById(team_id);
    }

    public String findTeamName(String teamName) {
        return teamRepository.findTeamName(teamName);
    }

    public Optional<Integer> findTeamId(String teamName) {
        return teamRepository.findTeamId(teamName);
    }

    public void createTeam(String name, String image_url){
        Team t = new Team(image_url, name, 0, 0, 0, 0);
        teamRepository.save(t);
    }

    public boolean updateTeam(int team_id, String newName, String newImageUrl) {
        Optional<Team> ot = findTeam(team_id);
        if (ot.isPresent()) {
            Team t = ot.get();
            if (!newName.equals(""))
                t.setName(newName);
            if (!newImageUrl.equals(""))
                t.setImage_url(newImageUrl);
            teamRepository.save(t);
            return true;
        }
        return false;
    }

    public String[] getTeamNames(){return teamRepository.getTeamsNames();}

    public Team[] getTeams() {
        return teamRepository.getTeams();
    }

    public void addWin(int team_id) {
        Optional<Team> t = findTeam(team_id);
        if (t.isPresent()) {
            Team t2 = t.get();
            t2.setWins(t2.getWins() + 1);
            teamRepository.save(t2);
        }
    }

    public void addLoss(int team_id) {
        Optional<Team> t = findTeam(team_id);
        if (t.isPresent()) {
            Team t2 = t.get();
            t2.setLosses(t2.getLosses() + 1);
            teamRepository.save(t2);
        }
    }

    public void addDraw(int team_id) {
        Optional<Team> t = findTeam(team_id);
        if (t.isPresent()) {
            Team t2 = t.get();
            t2.setDraws(t2.getDraws() + 1);
            teamRepository.save(t2);
        }
    }

    public void addGame(int team_id) {
        Optional<Team> t = findTeam(team_id);
        if (t.isPresent()) {
            Team t2 = t.get();
            t2.setGames(t2.getGames() + 1);
            teamRepository.save(t2);
        }
    }

    public Optional<Team> findTeam(int team_id){return teamRepository.findById(team_id);}
}
