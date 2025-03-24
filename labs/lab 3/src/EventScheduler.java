import java.util.*;

/** Tracks world events that have been scheduled. */
public final class EventScheduler {
    /** All current events. */
    private final PriorityQueue<Event> eventQueue;

    /** All current events sorted by actor, must be synchronized with 'eventQueue'. */
    private final Map<Actor, List<Event>> pendingEvents;

    /** The current time in the world. */
    private double currentTime;

    public EventScheduler() {
        this.eventQueue = new PriorityQueue<>();
        this.pendingEvents = new HashMap<>();
        this.currentTime = 0;
    }

    /** Queues an actor's event. */
    public void scheduleEvent(Actor actor, Update update, double afterPeriod) {
        double time = this.currentTime + afterPeriod;

        Event event = new Event(update, time, actor);

        this.eventQueue.add(event);

        // Synchronize list of pending events for the given actor
        List<Event> pending = this.pendingEvents.getOrDefault(actor, new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(actor, pending);
    }

    /** Removes all events of a given an actor. */
    public void unscheduleAllEvents(Actor actor) {
        List<Event> pending = this.pendingEvents.remove(actor);

        if (pending != null) {
            for (Event event : pending) {
                this.eventQueue.remove(event);
            }
        }
    }

    /** Removes an event from the pending list to synchronize with the queue. */
    public void removePendingEvent(Event event) {
        List<Event> pending = this.pendingEvents.get(event.getEntity());

        if (pending != null) {
            pending.remove(event);
        }
    }

    /** Execute all activites to the given time. */
    public void updateOnTime(double time) {
        double stopTime = this.currentTime + time;

        while (!this.eventQueue.isEmpty() && this.eventQueue.peek().getTime() <= stopTime) {
            Event next = this.eventQueue.poll();
            removePendingEvent(next);
            this.currentTime = next.getTime();
            next.getAction().execute(this);
        }

        this.currentTime = stopTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }
}
