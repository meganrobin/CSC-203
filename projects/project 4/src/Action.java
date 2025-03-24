/** A scheduled action to be carried out by a specific entity. */
public interface Action {
    /** Called when the action's scheduled time occurs. */
    void execute(EventScheduler scheduler);
}