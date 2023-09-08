package com.scoreDei.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scoreDei.AcessToken;
import com.scoreDei.services.PlayerService;
import com.scoreDei.services.TeamPlayerService;
import com.scoreDei.services.TeamService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Optional;

@Controller
public class PopulateController {
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    TeamPlayerService teamPlayerService;

    @GetMapping("/populate")
    public String populate(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();

        if (!at.getPermission(request)) {
            m.addAttribute("result", "ERROR: PERMISSION DENIED");

            return "error";
        }

        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/teams?league=94&season=2021")
                   .header("x-rapidapi-key", "ee8bce7c15d65828cb7dd4ad7658e04d")
                   .header("x-rapidapi-host", "v3.football.api-sports.io")
                   .header("league", "94")
                   .header("season", "2021")
                   .asString();

            JSONObject json = new JSONObject(response);
            JSONObject body = new JSONObject(json.getString("body"));
            JSONArray teams = body.getJSONArray("response");

            for (int i = 0; i < teams.length(); i++){
                JSONObject teamJSON = teams.getJSONObject(i);
                JSONObject team = teamJSON.getJSONObject("team");

                String teamImage = team.getString("logo");
                String teamName = team.getString("name");
                int teamId = team.getInt("id");

                String name = teamService.findTeamName(teamName);
                if (name == null) teamService.createTeam(teamName, teamImage);
                if (teamPlayerService.getTeamPlayers(teamService.findTeamId(teamName).get()).length != 0)
                    continue;

                response = Unirest.get("https://v3.football.api-sports.io/players?season=2021&team="+teamId)
                        .header("x-rapidapi-key", "ee8bce7c15d65828cb7dd4ad7658e04d")
                        .header("x-rapidapi-host", "v3.football.api-sports.io")
                        .header("league", String.format("%d", teamId))
                        .header("season", "2021")
                        .asString();

                json = new JSONObject(response);
                body = new JSONObject(json.getString("body"));
                JSONArray players = body.getJSONArray("response");

                for (int j = 0; j < players.length(); j++) {
                    JSONObject playerJSON = players.getJSONObject(j);
                    JSONObject player = playerJSON.getJSONObject("player");

                    String playerName = player.getString("name");

                    JSONObject birth = player.getJSONObject("birth");
                    if (!(birth.get("date") instanceof String)) continue;
                    Date playerBirthDate = Date.valueOf(birth.getString("date"));

                    JSONArray statistics = playerJSON.getJSONArray("statistics");
                    JSONObject game1 = statistics.getJSONObject(0);
                    JSONObject games = game1.getJSONObject("games");
                    String playerPosition = games.getString("position");

                    name = playerService.findPlayerName(playerName);
                    if (name == null){
                        playerService.createPlayer(playerName, playerPosition, playerBirthDate);

                        int playerID = playerService.findPlayerId(playerName);
                        Optional<Integer> teamID = teamService.findTeamId(teamName);
                        teamPlayerService.createTeamPlayer(teamID.get(), playerID);
                    }
                }
            }
       }
       catch (UnirestException e) {
            m.addAttribute("result", "UNIREST EXCEPTION");
            return "error";
       }

        m.addAttribute("result",  "DATABASE SUCCESSFULLY POPULATED");
        return "redirect:/menu";
   }
}
