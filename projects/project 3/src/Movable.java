public interface Movable {
    boolean moveTo(World world, Entity target, EventScheduler scheduler);
    Point nextPosition(World world, Point destination);
}