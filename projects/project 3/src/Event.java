/** An event owned by an entity that will occur at a specific time. */
public final class Event implements Comparable<Event> {
    /** The timestamp, in seconds, at which the event occurs. */
    private final double time;

    /** The entity that "owns" with the event. */
    private final Entity entity;

    /** The action to carry out when the event occurs. */
    private final Action action;

    public Event(Action action, double time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    public Action getAction() {
        return action;
    }

    public double getTime() {
        return time;
    }

    public Entity getEntity() {
        return entity;
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
