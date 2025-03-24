/** An event owned by an Actor that will occur at a specific time. */
public final class Event implements Comparable<Event> {
    /** The timestamp, in seconds, at which the event occurs. */
    private final double time;

    /** The actor that "owns" with the event. */
    private final Actor actor;

    /** The action to carry out when the event occurs. */
    private final Update update;

    public Event(Update update, double time, Actor actor) {
        this.update = update;
        this.time = time;
        this.actor = actor;
    }

    public Update getAction() {
        return update;
    }

    public double getTime() {
        return time;
    }

    public Actor getEntity() {
        return actor;
    }

    /**
     * Compare this Event with another for order based on time in milliseconds.
     * Returns a negative integer if this object is ordered before the other.
     * Returns 0 if this object and the other are ordered at the same position.
     * Returns a positive integer if this object is ordered after the other.
     *
     * @param other the Event to be compared.
     * @return An integer based on this objects ordering.
     */
    @Override
    public int compareTo(Event other) {
        return Double.compare(time, other.time);
    }
}
