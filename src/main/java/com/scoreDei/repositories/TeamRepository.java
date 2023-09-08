package com.scoreDei.repositories;

import com.scoreDei.data.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    @Query("SELECT t.name FROM Team t WHERE t.team_id=?1")
    public String findTeamNameById(int team_id);

    @Query("SELECT t.name FROM Team t WHERE t.name=?1")
    public String findTeamName(String teamName);

    @Query("SELECT t.team_id FROM Team t WHERE t.name=?1")
    public Optional<Integer> findTeamId(String teamName);

    @Query("SELECT t.name FROM Team t WHERE t.team_id <> 0")
    public String[] getTeamsNames();

    @Query("SELECT t FROM Team t WHERE t.team_id <> 0")
    public Team[] getTeams();

}
