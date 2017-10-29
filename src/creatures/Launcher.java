package creatures;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Just a simple test of the simulator.
 *
 */
@SuppressWarnings("serial")
public class Launcher extends JFrame {

    public Launcher() {
        Environment environment = new Environment(640, 480);
        setName("Creature Simulator");
        setSize(640, 480);
        setLayout(new BorderLayout());

        add(environment, BorderLayout.CENTER);
        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exit(evt);
            }
        });
    }

    private void exit(WindowEvent evt) {
        System.exit(0);
    }

    public static void main(String args[]) {
        new Launcher().setVisible(true);
    }

}
