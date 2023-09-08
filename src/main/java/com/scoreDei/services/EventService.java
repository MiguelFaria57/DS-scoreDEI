package com.scoreDei.services;

import com.scoreDei.data.Event;
import com.scoreDei.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEvent(Event e) {
        eventRepository.save(e);
    }

    public boolean changeEventValidation(int event_id) {
        Optional<Event> e = getEvent(event_id);
        if (e.isPresent()) {
            Event ev = e.get();
            System.out.println(ev.getType());
            ev.setIs_valid(!ev.isIs_valid());
            eventRepository.save(ev);
            return true;
        }
        return false;
    }

    public Optional<Event> getEvent(int event_id) {
        return eventRepository.findById(event_id);
    }

    public Event[] getValidEvents(int match_id ){return eventRepository.getValidEvents(match_id);}

    public Event[] getEvents(int match_id ){return eventRepository.getEvents(match_id);}

    public Event[] getValidGoals(){return eventRepository.getValidGoals();}

    public Event[] getMatchValidGoals(int match_id) {
        return eventRepository.getMatchValidGoals(match_id);
    }

    public Event[] getMatchGoalsByPlayer(int match_id,int player_id){return eventRepository.getMatchGoalsByPlayer(match_id,player_id);}

    public Event getEventbyID(int event_id ){return eventRepository.getEventbyID(event_id);}

}
