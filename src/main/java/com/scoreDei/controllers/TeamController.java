package com.scoreDei.controllers;

import com.scoreDei.data.Player;
import com.scoreDei.data.Team;
import com.scoreDei.forms.FormTeam;
import com.scoreDei.services.PlayerService;
import com.scoreDei.services.TeamPlayerService;
import com.scoreDei.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import com.scoreDei.AcessToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamPlayerService teamplayerService;
    @Autowired
    private PlayerService playerService;

    private String result = "\n";

    @GetMapping("/regteam")
    public String registerteam(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();
        boolean permission = at.getPermission(request);
        if (permission){
            m.addAttribute("team", new FormTeam());
            m.addAttribute("result",result);
            result = "\n";
            return "regteam";
        }
        m.addAttribute("result", "PERMISSION DENIED");
        return "error";
    }

    @PostMapping("/regteam")
    public String saveteam(@ModelAttribute FormTeam t, Model m){

        String success = teamService.findTeamName(t.getName());
        if(success == null && !t.getName().equals("")){
            teamService.createTeam(t.getName(), t.getImage_url());
            result = "TEAM CREATED";
            return "redirect:/regteam";
        }
        else if(t.getName().equals("")){
            result = "ERROR: INVALID FIELDS";
            return "redirect:/regteam";
        }

        result = "ERROR: DUPLICATE TEAM NAME";
        return "redirect:/regteam";
    }

    @GetMapping("/allteams")
    public String allteams(HttpServletRequest request, Model m){
        Team[] teams = teamService.getTeams();
        Arrays.sort(teams, Comparator.comparing(Team::getTeam_id));
        m.addAttribute("teams",teams);
        return "allteams";
    }

    @GetMapping("/allteams/{team_id}")
    public String team(@PathVariable("team_id") int team_id, Model m, HttpServletRequest request){
        AcessToken at = new AcessToken();
        if (at.getPermission(request)) m.addAttribute("permissionAdmin", "1");
        else m.addAttribute("permissionAdmin", "0");
        m.addAttribute("team_id", team_id);

        Optional<Team> team = teamService.findTeam(team_id);
        if(team.isEmpty()) {
            m.addAttribute("result", "ERROR: TEAM ID DOEST NOT EXIST");
            return "error";
        }
        m.addAttribute("team", team.get());
        int[] players_ids = teamplayerService.getTeamPlayers(team_id);
        if(players_ids.length==0){
            m.addAttribute("players",new Player[0]);
            return "team";
        }
        Player[] players = new Player[players_ids.length];
        int count = 0;
        for (int i: players_ids) {
            Optional<Player> p = playerService.getPlayer(i);
            players[count] = p.get();
            count += 1;
        }

        m.addAttribute("players", players);
        return "team";
    }

    @GetMapping("/allteams/{team_id}/manageteam")
    public String manageTeam(HttpServletRequest request, @PathVariable("team_id") int team_id, Model m) {
        AcessToken at = new AcessToken();
        if (!at.getPermission(request)) {
            m.addAttribute("result", "PERMISSION DENIED");
            return "error";
        }

        m.addAttribute("team_id", team_id);
        m.addAttribute("ft", new FormTeam());

        Player[] players = playerService.getAllPlayers();
        ArrayList<Player> listPlayer = new ArrayList<>();
        Collections.addAll(listPlayer, players);
        int[] team_players_ids = teamplayerService.getTeamPlayers(team_id);
        for(int i=0; i<listPlayer.size(); i++) {
            for (int j : team_players_ids) {
                if (j == listPlayer.get(i).getPlayer_id()) {
                    listPlayer.remove(i);
                    break;
                }
            }
        }
        Player[] listPlayers = listPlayer.toArray(new Player[0]);
        Arrays.sort(listPlayers, Comparator.comparing(Player::getName));
        m.addAttribute("players", listPlayers);
        if (team_players_ids.length > 0) {
            Player[] team_players = new Player[team_players_ids.length];
            int count = 0;
            for (int i : team_players_ids) {
                team_players[count] = playerService.getPlayer(i).get();
                count += 1;
            }
            Arrays.sort(team_players, Comparator.comparing(Player::getName));
            m.addAttribute("team_players", team_players);
        }
        else {
            result = "ERROR: TEAM DOESNT HAVE PLAYERS";
            m.addAttribute("team_players", new Player[0]);
        }

        return "manageteam";
    }

    @PostMapping("/allteams/{team_id}/manageteam")
    public String manageTeam(@PathVariable("team_id") int team_id, @ModelAttribute FormTeam ft) {
        boolean check = teamService.updateTeam(team_id, ft.getName(), ft.getImage_url());
        if(!check){
            result = "ERROR TRYING TO UPDATE PLAYER";
            return "redirect:/allteams/{team_id}/manageteam";
        }

        return "redirect:/allteams/{team_id}";
    }
}
