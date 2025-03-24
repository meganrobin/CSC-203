public interface Movable {
    boolean moveTo(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary);
    Point nextPosition(World world, Point destination);
}