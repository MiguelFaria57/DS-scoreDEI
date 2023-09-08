package com.scoreDei.controllers;

import com.scoreDei.AcessToken;
import com.scoreDei.data.Event;
import com.scoreDei.data.Match;
import com.scoreDei.forms.FormMatch;
import com.scoreDei.services.EventService;
import com.scoreDei.services.MatchService;
import com.scoreDei.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;


@Controller
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EventService eventService;

    private String result = "\n";

    @GetMapping("/regmatch")
    public String registermatch(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();
        boolean permission = at.getPermission(request);
        if (permission){
            String[] teams = teamService.getTeamNames();
            m.addAttribute("match", new FormMatch());
            m.addAttribute("result",result);
            m.addAttribute("teams",teams);
            result = "\n";
            return "regmatch";
        }
        m.addAttribute("result", "PERMISSION DENIED");
        return "error";
    }

    @PostMapping("/regmatch")
    public String savematch(@ModelAttribute FormMatch fm){
        if (fm.getLocation().equals("")) {
            return "redirect:/regmatch";
        }

        if (!fm.getTeam_a().equals(fm.getTeam_b())) {
            int team_a_id = teamService.findTeamId(fm.getTeam_a()).get();
            int team_b_id = teamService.findTeamId(fm.getTeam_b()).get();
            matchService.createMatch(fm.getLocation(), fm.getMatch_date(), team_a_id, team_b_id);
            result = "MATCH CREATED";
            return "redirect:/regmatch";
        }
        else {
            result = "ERROR";
            return "redirect:/regmatch";
        }
    }

    @GetMapping("/allmatches/{match}")
    public String match(@PathVariable("match") int match_id, Model m, HttpServletRequest request){
        Optional<Match> matchOp = matchService.getMatch(match_id);

        if(matchOp.isEmpty()){
            m.addAttribute("result","ERROR: MATCH DOES NOT EXIST");
            return "error";
        }

        Match match = matchOp.get();
        String teamAname = teamService.findTeamNameById(match.getTeam_a_id());
        String teamBname = teamService.findTeamNameById(match.getTeam_b_id());
        Event[] events = eventService.getValidEvents(match.getMatch_id());
        Arrays.sort(events, Comparator.comparing(Event::getEvent_date));
        int teamAscore = 0;
        int teamBscore = 0;

        for (Event e : events)
            if (e.getType().equals("Goal") && e.getTeam_id() == match.getTeam_a_id())
                teamAscore++;
            else if (e.getType().equals("Goal") && e.getTeam_id() == match.getTeam_b_id())
                teamBscore++;

        m.addAttribute("name", teamAname + " vs " + teamBname);
        m.addAttribute("scores", teamAscore + " : " + teamBscore);
        m.addAttribute("location", match.getLocation());
        m.addAttribute("date", match.getMatch_date());
        m.addAttribute("events", events);
        m.addAttribute("match_id",match_id);
        AcessToken at = new AcessToken();
        if (at.getPermission(request)) m.addAttribute("permissionAdmin", "1");
        else m.addAttribute("permissionAdmin", "0");
        if (at.hasToken(request)) m.addAttribute("permissionUser", "1");
        else m.addAttribute("permissionUser", "0");

        return "match";
    }

    @GetMapping("/allmatches")
    public String matches(HttpServletRequest request, Model m){
        AcessToken at = new AcessToken();
        m.addAttribute("token", at.hasToken(request));
        Match[] matches = matchService.getMatches();
        FormMatch[] fm = new FormMatch[matches.length];
        int cont = 0;
        for(Match ma : matches){
            FormMatch aux = new FormMatch();
            String teamA = teamService.findTeamNameById(ma.getTeam_a_id());
            String teamB = teamService.findTeamNameById(ma.getTeam_b_id());
            aux.setTeam_a(teamA);
            aux.setTeam_b(teamB);
            aux.setLocation(ma.getLocation());
            aux.setMatch_id(ma.getMatch_id());
            aux.setMatch_date(ma.getMatch_date());
            fm[cont] = aux;
            cont+=1;
        }
        m.addAttribute("matches",fm);
        return "allmatches";
    }


    @GetMapping("/allmatches/{match_id}/managematch")
    public String managematch(HttpServletRequest request, @PathVariable("match_id") int match_id,Model m){
        AcessToken at = new AcessToken();
        if (!at.getPermission(request)) {
            m.addAttribute("result", "PERMISSION DENIED");
            return "error";
        }

        FormMatch fm = new FormMatch();
        fm.setMatch_id(match_id);
        m.addAttribute("match_id",match_id);
        m.addAttribute("fm",fm);
        m.addAttribute("result",result);
        result = "\n";
        return "managematch";
    }

    @PostMapping("/allmatches/{match_id}/managematch")
    public String updatematch(@PathVariable("match_id") int match_id, @ModelAttribute FormMatch fm){
        boolean check = matchService.updateMatch(match_id,fm.getLocation(),fm.getMatch_date());
        if(!check){
            result = "ERROR TRYING TO UPDATE MATCH";
            return "redirect:/allmatches/{match_id}/managematch";
        }
        return "redirect:/allmatches/{match_id}";
    }
}
