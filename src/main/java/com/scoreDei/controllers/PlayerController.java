package com.scoreDei.controllers;

import com.scoreDei.AcessToken;
import com.scoreDei.data.Player;
import com.scoreDei.data.Team;
import com.scoreDei.forms.FormPlayer;
import com.scoreDei.services.PlayerService;
import com.scoreDei.services.TeamPlayerService;
import com.scoreDei.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Controller
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamPlayerService teamPlayerService;
    private String result = "\n";

    @GetMapping("/regplayer")
    public String registerplayer(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();
        boolean permission = at.getPermission(request);
        if (permission){
            m.addAttribute("player", new FormPlayer());
            m.addAttribute("result",result);
            result = "\n";
            return "regplayer";
        }
        m.addAttribute("result", "ERROR: PERMISSION DENIED");
        return "error";
    }

    @PostMapping("/regplayer")
    public String saveplayer(@ModelAttribute FormPlayer p){
        String success = playerService.findPlayerName(p.getName());
        if(success == null && !p.getName().equals("")){
            Optional<Integer> teamId = teamService.findTeamId(p.getTeam_name());
            if(teamId.isEmpty()){
                result = "ERROR: TEAM NAME DOESNT EXIST";
                return "redirect:/regplayer";
            }
            playerService.createPlayer(p.getName(), p.getPosition(), p.getBirth_date());
            int playerId = playerService.findPlayerId(p.getName());
            teamPlayerService.createTeamPlayer(teamId.get(), playerId);
            result = "PLAYER INSERTED";
            return "redirect:/regplayer";
        }

        result = "ERROR: PLAYER NAME ALREADY EXISTS";
        return "redirect:/regplayer";
    }

    @GetMapping("/allplayers")
    public String allplayer(Model m){
        Player[] players = playerService.getAllPlayers();
        Arrays.sort(players, Comparator.comparing(Player::getPlayer_id));
        m.addAttribute("players",players);
        return "allplayers";
    }

    @GetMapping("/allplayers/{player_id}")
    public String team(@PathVariable("player_id") int player_id, Model m, HttpServletRequest request){
        AcessToken at = new AcessToken();
        if (at.getPermission(request)) m.addAttribute("permissionAdmin", "1");
        else m.addAttribute("permissionAdmin", "0");
        m.addAttribute("player_id", player_id);

        Optional<Player> player = playerService.getPlayer(player_id);
        if(player.isEmpty()){
            m.addAttribute("result","ERROR: PLAYER ID DOEST NOT EXIST");
            return "error";
        }
        m.addAttribute("player",player.get());
        int[] teams_ids = teamPlayerService.getPlayerTeams(player_id);
        if(teams_ids.length==0){
            m.addAttribute("result",new Team[0]);
            return "player";
        }
        Team[] teams = new Team[teams_ids.length];
        int count = 0;
        for(int ti: teams_ids){
            Optional<Team> t = teamService.findTeam(ti);
            Team team = t.get();
            teams[count]=team;
            count+= 1;

        }

        m.addAttribute("teams",teams);
        
        return "player";
    }

    @GetMapping("/allplayers/{player_id}/manageplayer")
    public String manageplayer(@PathVariable("player_id") int player_id, Model m, HttpServletRequest request) {

        AcessToken at = new AcessToken();
        if(!at.getPermission(request)){
            m.addAttribute("result", "PERMISSION DENIED");
            return "error";
        }
        FormPlayer fp = new FormPlayer();
        m.addAttribute("player_id", player_id);
        m.addAttribute("fp",fp);
        m.addAttribute("result",result);
        result = "\n";
        return "manageplayer";
    }

    @PostMapping("/allplayers/{player_id}/manageplayer")
    public String updatematch(@PathVariable("player_id") int player_id, @ModelAttribute FormPlayer fp){
        boolean check = playerService.updatePlayer(player_id,fp.getName(),fp.getPosition(), fp.getBirth_date());
        if(!check){
            result = "ERROR TRYING TO UPDATE PLAYER";
            return "redirect:/allplayers/{player_id}/manageplayer";
        }
        return "redirect:/allplayers/{player_id}";
    }
}
