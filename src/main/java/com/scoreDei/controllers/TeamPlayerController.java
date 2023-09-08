package com.scoreDei.controllers;

import com.scoreDei.AcessToken;
import com.scoreDei.forms.FormTeam;
import com.scoreDei.services.PlayerService;
import com.scoreDei.services.TeamPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TeamPlayerController {
    @Autowired
    TeamPlayerService teamPlayerService;

    @Autowired
    PlayerService playerService;

    @PostMapping("/allteams/{team_id}/manageteam/addplayer")
    public String addplayer(@ModelAttribute("ft") FormTeam ft, @PathVariable("team_id") int team_id, HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();

        if (!at.getPermission(request)) {
            m.addAttribute("result", "ERROR: PERMISSION DENIED");

            return "error";
        }

        int player_id = playerService.findPlayerId(ft.getName());
        teamPlayerService.createTeamPlayer(team_id, player_id);

        return "redirect:/allteams/{team_id}";
    }

    @PostMapping("/allteams/{team_id}/manageteam/removeplayer")
    public String removeplayer(@ModelAttribute("ft") FormTeam ft, HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();

        if (!at.getPermission(request)) {
            m.addAttribute("result", "ERROR: PERMISSION DENIED");

            return "error";
        }

        int player_id = playerService.findPlayerId(ft.getName());
        teamPlayerService.deleteTeamPlayerById(player_id);

        return "redirect:/allteams/{team_id}";
    }
}
