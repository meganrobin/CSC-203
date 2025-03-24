import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView {
    private PApplet screen;
    private int scale;
    private World world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, int scale, World world, int tileWidth, int tileHeight) {
        this.screen = screen;
        this.scale = scale;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = NumberUtil.clamp(viewport.getCol() + colDelta, 0, world.getNumCols() - viewport.getNumCols());
        int newRow = NumberUtil.clamp(viewport.getRow() + rowDelta, 0, world.getNumRows() - viewport.getNumRows());

        this.viewport.shift(newCol, newRow);
    }

    public void drawViewport() {
        this.drawBackground();
        this.drawEntities();
    }

    public void drawBackground() {
        for (int row = 0; row < viewport.getNumRows(); row++) {
            for (int col = 0; col < viewport.getNumCols(); col++) {
                Point worldPoint = viewport.viewportToWorld(col, row);
                Optional<Background> background = world.getBackground(worldPoint);
                if (background.isPresent()) {
                    screen.image(background.get().getImage(), col * tileWidth * scale, row * tileHeight * scale, tileWidth * scale, tileHeight * scale);
                }
            }
        }
    }

    public void drawEntities() {
        for (Entity entity : world.getEntities()) {
            Point pos = entity.getPosition();

            if (viewport.contains(pos)) {
                Point viewPoint = viewport.worldToViewport(pos.x, pos.y);

                screen.image(
                        entity.getImages().get(entity.getImageIndex() % entity.getImages().size()),
                        viewPoint.x * tileWidth * scale,
                        viewPoint.y * tileHeight * scale,
                        tileWidth * scale,
                        tileHeight * scale
                );
            }
        }
    }

    /*
     * Getters and Setters.
     */
    public Viewport getViewport() {
        return viewport;
    }
}
