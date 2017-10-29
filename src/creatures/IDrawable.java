package creatures;

import java.awt.*;

public interface IDrawable {
    public Color getColor();

    public int getSize();

    /**
     * dessine une creature dans le canvas
     * @param graphics2D
     */
    public void paint(Graphics2D graphics2D);
}
