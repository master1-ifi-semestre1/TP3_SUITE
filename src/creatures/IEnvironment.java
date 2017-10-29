package creatures;

import java.awt.*;

public interface IEnvironment {
    public Iterable<ICreature> getCreatures();

    public Dimension getSize();
}
