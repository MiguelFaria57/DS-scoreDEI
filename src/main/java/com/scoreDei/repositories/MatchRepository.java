package com.scoreDei.repositories;

import com.scoreDei.data.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MatchRepository extends CrudRepository<Match, Integer> {
    @Query(value="SELECT m.match_id FROM Match m WHERE m.team_a_id=?1 OR m.team_b_id=?1")
    public ArrayList<Integer> getTeamMatchesIds(int team_id);

    @Query(value="SELECT m.match_id FROM Match m WHERE m.team_a_id=?1 AND m.team_b_id=?2 OR m.team_a_id=?2 AND m.team_b_id=?1")
    public int[] getMatchesBetweenTeams(int team_a_id, int team_b_id);

    @Query(value="SELECT m.team_a_id FROM Match m WHERE m.match_id=?1")
    public int getTeamAbyMatchID(int match_id);

    @Query(value="SELECT m.team_b_id FROM Match m WHERE m.match_id=?1")
    public int getTeamBbyMatchID(int match_id);

    @Query(value="SELECT m FROM Match m")
    public Match[] getMatches();
}
