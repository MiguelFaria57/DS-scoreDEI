package com.scoreDei.services;

import com.scoreDei.data.Match;
import com.scoreDei.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public void createMatch(String location, Date match_date, int team_a_id, int team_b_id) {
        Match m = new Match(location, match_date, team_a_id, team_b_id);
        matchRepository.save(m);
    }

    public boolean updateMatch(int match_id, String newLocation, Date newDate) {
        Optional<Match> om = getMatch(match_id);
        if (om.isPresent()) {
            Match m = om.get();
            if (!newLocation.equals(""))
                m.setLocation(newLocation);
            m.setMatch_date(newDate);
            matchRepository.save(m);
            return true;
        }
        return false;
    }

    public Optional<Match> getMatch(int match_id) {
        return matchRepository.findById(match_id);
    }

    public ArrayList<Integer> getTeamMatchesIds(int team_id) {
        return matchRepository.getTeamMatchesIds(team_id);
    }

    public int[] getMatchesBetweenTeams(int team_a_id, int team_b_id) {
        return matchRepository.getMatchesBetweenTeams(team_a_id, team_b_id);
    }

    public int getTeamAbyMatchID(int match_id){return matchRepository.getTeamAbyMatchID(match_id);}

    public int getTeamBbyMatchID(int match_id){return matchRepository.getTeamBbyMatchID(match_id);}

    public Match[] getMatches(){return matchRepository.getMatches();}

}
