package com.scoreDei.controllers;

import com.scoreDei.data.Event;
import com.scoreDei.data.Team;
import com.scoreDei.forms.FormPlayerStatistics;
import com.scoreDei.forms.FormTeamVsTeam;
import com.scoreDei.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Controller
public class StatisticsController {

    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    EventService eventService;
    @Autowired
    MatchService matchService;
    @Autowired
    TeamPlayerService teamPlayerService;
    String result = "\n";

    @GetMapping("/statistics")
    public String stats(Model m){
        m.addAttribute("teamvsteam", new FormTeamVsTeam());
        Team[] teams = teamService.getTeams();
        Arrays.sort(teams, Comparator.comparing(Team::getTeam_id));
        m.addAttribute("teams", teams);
        String[] teamNames = teamService.getTeamNames();
        Arrays.sort(teamNames);
        m.addAttribute("teamNames", teamNames);

        Event[] goals = eventService.getValidGoals();
        int[] bestPlayerInfo = mostGoals(goals);
        String name = playerService.findPlayerNameById(bestPlayerInfo[0]);
        int[] playerTeams = teamPlayerService.getPlayerTeams(bestPlayerInfo[0]);
        int numMatches = 0;
        ArrayList<Integer> matches_ids = new ArrayList<>();
        for(int pt : playerTeams){
            ArrayList<Integer> ma = matchService.getTeamMatchesIds(pt);
            matches_ids.addAll(ma);
            numMatches += ma.size();
        }
        float mean = (float)bestPlayerInfo[1]/numMatches;
        int[] bestMatch = {0,0};
        for (int i: matches_ids) {
            Event[] matchGoalsByPlayer = eventService.getMatchGoalsByPlayer(i, bestPlayerInfo[0]);
            if (matchGoalsByPlayer.length > bestMatch[1]) {
                bestMatch[0] = i;
                bestMatch[1] = matchGoalsByPlayer.length;
            }
        }

        FormPlayerStatistics fps = new FormPlayerStatistics(name,bestPlayerInfo[1],mean,bestMatch[0],bestMatch[1]);
        m.addAttribute("fps",fps);
        m.addAttribute("teamA","");
        m.addAttribute("teamB","");
        m.addAttribute("result",result);
        result = "\n";
        return "statistics";
    }

    public int[] mostGoals(Event[] arr){

        if(arr.length==0){
            return new int[]{1,0};
        }

        Arrays.sort(arr,  Comparator.comparing(Event::getPlayer_id));
        int max_count = 1, res = arr[0].getPlayer_id();
        int curr_count = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i].getPlayer_id() == arr[i - 1].getPlayer_id())
                curr_count++;
            else
                curr_count = 1;

            if (curr_count > max_count) {
                max_count = curr_count;
                res = arr[i - 1].getPlayer_id();
            }
        }

        int[] info = new int[2];
        info[0] = res; // player id
        info[1] = max_count; // number of goals
        return info;
    }

    @GetMapping("/teamsvsteam")
    public String teamvsteam(@ModelAttribute FormTeamVsTeam ftvst, Model m){
        if(ftvst.getTeam_a().equals(ftvst.getTeam_b())){
            result = "ERROR";
            return "redirect:/statistics";
        }

        int teamA_id = teamService.findTeamId(ftvst.getTeam_a()).get();
        int teamB_id = teamService.findTeamId(ftvst.getTeam_b()).get();
        int[] matches_ids = matchService.getMatchesBetweenTeams(teamA_id,teamB_id);
        int vicA = 0;
        int vicB = 0;
        int dr = 0;
        int goals = 0;
        int rc = 0;
        int yc = 0;

        for (int match_id : matches_ids){
            Event[] events = eventService.getValidEvents(match_id);
            int ga = 0;
            int gb = 0;
            boolean ended = false;
            for(Event e : events){
                if(e.getType().equals("Goal")){
                    if(e.getTeam_id()==teamA_id)
                        ga += 1;
                    else if(e.getTeam_id()==teamB_id)
                        gb += 1;
                    goals += 1;
                }
                else if(e.getType().equals("Red_Card"))
                    rc += 1;
                else if(e.getType().equals("Yellow_Card"))
                    yc += 1;
                else if(e.getType().equals("Match_End"))
                    ended = true;
            }
            if (ended) {
                if (ga > gb)
                    vicA += 1;
                else if (ga < gb)
                    vicB += 1;
                else
                    dr += 1;
            }
        }

        m.addAttribute("teamA",ftvst.getTeam_a());
        m.addAttribute("teamB",ftvst.getTeam_b());
        m.addAttribute("vicA",vicA);
        m.addAttribute("vicB",vicB);
        m.addAttribute("draw",dr);
        m.addAttribute("goals",goals);
        m.addAttribute("rc",rc);
        m.addAttribute("yc",yc);

        return "teamvsteam";
    }


}
