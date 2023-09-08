package com.scoreDei.repositories;

import com.scoreDei.data.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.match_id=?1 AND e.is_valid = True")
    public Event[] getValidEvents(int match_id );

    @Query("SELECT e FROM Event e WHERE e.type = 'Goal' AND e.is_valid = True")
    public Event[] getValidGoals();

    @Query("SELECT e FROM Event e WHERE e.type = 'Goal' AND e.is_valid = True AND e.match_id = ?1")
    public Event[] getMatchValidGoals(int match_id);

    @Query("SELECT e FROM Event e WHERE e.match_id=?1")
    public Event[] getEvents(int match_id );

    @Query("SELECT e FROM Event e WHERE e.match_id=?1 AND e.type = 'Goal' AND e.player_id=?2 AND e.is_valid = True")
    public Event[] getMatchGoalsByPlayer(int match_id,int player_id);

    @Query("SELECT e FROM Event e WHERE e.event_id=?1")
    public Event getEventbyID(int event_id );

}
