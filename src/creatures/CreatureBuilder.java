package creatures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class CreatureBuilder {
    public static final float MAX_SPEED = 10f;

    public List<AbstractCreature> createCreatures(IEnvironment environment, int number) {

        Dimension dim = environment.getSize();

        List<AbstractCreature> list = new ArrayList<AbstractCreature>();
        Random rand = new Random();

        // view the color space as a cube and then iterate over it using a small
        // steps
        float creaturesCountCubeRoot = (float) pow(number, 1.0 / 3.0);
        float colorPhase = (float) (1.0 / creaturesCountCubeRoot);
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;

        for (int i = 0; i < number; i++) {
            // X coordinate
            double x = (rand.nextDouble() * dim.getWidth()) - dim.getWidth() / 2;

            // Y coordinate
            double y = (rand.nextDouble() * dim.getHeight()) - dim.getHeight() / 2;

            // direction
            double direction = (rand.nextDouble() * 2 * PI);

            int speed = (int) (rand.nextDouble() * MAX_SPEED);
            r += colorPhase;
            if (r > 1.0) {
                r -= 1.0f;
                g += colorPhase;
                if (g > 1.0) {
                    g -= 1.0f;
                    b += colorPhase;
                    if (b > 1.0)
                        b -= 1.0f;
                }
            }
            list.add(new BouncingCreature(environment, x, y, direction, speed, new Color(
                    r, g, b)));
        }
        creaturesCount = number;
        return list;
    }

}
