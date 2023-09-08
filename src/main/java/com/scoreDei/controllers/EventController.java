package com.scoreDei.controllers;

import com.scoreDei.AcessToken;
import com.scoreDei.data.Event;
import com.scoreDei.data.Player;
import com.scoreDei.data.Team;
import com.scoreDei.forms.FormEvent;
import com.scoreDei.forms.FormMatchEvents;
import com.scoreDei.services.*;
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
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamPlayerService teamPlayerService;

    private String result = "\n";

    @GetMapping("/allmatches/{match}/regevent")
    public String regevent(HttpServletRequest request, @PathVariable("match") int match_id, Model m){
        AcessToken at = new AcessToken();
        if (!at.hasToken(request)) {
            m.addAttribute("result", "PERMISSION DENIED");
            return "error";
        }

        int teamAid = matchService.getTeamAbyMatchID(match_id);
        int teamBid = matchService.getTeamBbyMatchID(match_id);
        String teamA = teamService.findTeamNameById(teamAid);
        String teamB = teamService.findTeamNameById(teamBid);
        int[] playersIdsA = teamPlayerService.getTeamPlayers(teamAid);
        int[] playersIdsB = teamPlayerService.getTeamPlayers(teamBid);
        String[] players = new String[playersIdsA.length + playersIdsB.length];
        int count = 0;
        for (int id : playersIdsA) {
            players[count] = playerService.findPlayerNameById(id);
            count++;
        }
        for (int id : playersIdsB) {
            players[count] = playerService.findPlayerNameById(id);
            count++;
        }
        Arrays.sort(players);

        m.addAttribute("teamA", teamA);
        m.addAttribute("teamB", teamB);
        m.addAttribute("players", players);
        m.addAttribute("event", new FormEvent());
        m.addAttribute("match_id", match_id);
        m.addAttribute("result",result);
        result = "\n";
        return "regevent";
    }

    @PostMapping("/allmatches/{match}/regevent")
    public String saveevent(@ModelAttribute FormEvent fe, @PathVariable("match") int match_id) {
        Event e = new Event();
        e.setType(fe.getType());
        e.setMatch_id(match_id);
        e.setEvent_date(fe.getEvent_date());
        if (fe.getTeam_name()!=null){
            Optional<Integer> op = teamService.findTeamId(fe.getTeam_name());
            if (op.isPresent())
                e.setTeam_id(op.get());
            else {
                if (e.getType().equals("Goal")) {
                    result = "INVALID FIELDS";
                    return "redirect:/allmatches/{match}/regevent";
                }
                e.setTeam_id(0);
            }
        }
        else
            e.setTeam_id(0);

        if (fe.getPlayer_name()!=null){
            Optional<Integer> op = playerService.findOptionalID(fe.getPlayer_name());
            if (op.isPresent())
                e.setPlayer_id(op.get());
            else {
                if(e.getType().equals("Goal") || e.getType().equals("Red_Card") || e.getType().equals("Yellow_card")){
                    result = "INVALID FIELDS";
                    return "redirect:/allmatches/{match}/regevent";
                }
                e.setPlayer_id(0);
            }
        }
        else
            e.setPlayer_id(0);

        if (e.getType().equals("Match_Start") || e.getType().equals("Match_End") || e.getType().equals("Interrupted_Match") || e.getType().equals("Resumed_Match")) {
            e.setPlayer_id(0);
            e.setTeam_id(0);
        }
        else if (e.getType().equals("Red_Card") || e.getType().equals("Yellow_Card"))
            e.setTeam_id(0);

        eventService.createEvent(e);

        return "redirect:/allmatches/{match}";
    }

    @GetMapping("/allmatches/{match}/manageevent")
    public String validevent(HttpServletRequest request, @PathVariable("match") int match_id, Model m){
        AcessToken at = new AcessToken();

        if (at.getPermission(request)) {
            Event[] events = eventService.getEvents(match_id);
            Arrays.sort(events, Comparator.comparing(Event::getEvent_id));
            m.addAttribute("match_id", match_id);
            m.addAttribute("events", events);
            m.addAttribute("check", new FormMatchEvents());
            m.addAttribute("result",result);
            result = "\n";
            return "manageevent";
        }
        m.addAttribute("result", "ERROR: PERMISSION DENIED");
        return "error";
    }

    @PostMapping("/allmatches/{match}/manageevent")
    public String eventvalid(@ModelAttribute FormMatchEvents fme,@PathVariable("match") int match_id) {
        int event_id;
        try {
            event_id = Integer.parseInt(fme.getEvent_id());
        }
        catch (NumberFormatException e) {
            result = "INVALID EVENT ID";
            return "redirect:/allmatches/{match}/manageevent";
        }

        Event event = eventService.getEventbyID(event_id);
        if(event.getMatch_id()!=match_id){
            result = "EVENT ID DOESNT BELONG TO THIS MATCH";
            return "redirect:/allmatches/{match}/manageevent";
        }

        if(!eventService.changeEventValidation(event_id)) {
            result = "EVENT ID NOT FOUND";
            return "redirect:/allmatches/{match}/manageevent";
        }

        if(event.getType().equals("Match_End") && event.isIs_valid()){
            System.out.println("          1");
            Event[] events = eventService.getMatchValidGoals(event.getMatch_id());
            int ga = 0;
            int gb = 0;
            int teamA_id = matchService.getTeamAbyMatchID(event.getMatch_id());
            int teamB_id = matchService.getTeamBbyMatchID(event.getMatch_id());

            if (events.length > 0) {
                for (Event e : events) {
                    if (e.getType().equals("Goal")) {
                        if (e.getTeam_id() == teamA_id)
                            ga += 1;
                        else if (e.getTeam_id() == teamB_id)
                            gb += 1;
                    }
                }
            }
            if (ga > gb) {
                teamService.addWin(teamA_id);
                teamService.addLoss(teamB_id);
                teamService.addGame(teamA_id);
                teamService.addGame(teamB_id);
            }
            else if (ga < gb) {
                teamService.addWin(teamB_id);
                teamService.addLoss(teamA_id);
                teamService.addGame(teamA_id);
                teamService.addGame(teamB_id);
            }
            else {
                System.out.println("          2");
                teamService.addDraw(teamA_id);
                teamService.addDraw(teamB_id);
                teamService.addGame(teamA_id);
                teamService.addGame(teamB_id);
            }
        }

        return "redirect:/allmatches/{match}/manageevent";
        }


}
