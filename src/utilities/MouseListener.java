package utilities;

import javax.sound.sampled.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author Jaffer
 *
 * used to make sound on button click
 * unused because we didn't have time
 */
public class MouseListener {


    public static void mouseClicked(AudioInputStream input) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        Clip clip=AudioSystem.getClip();
        clip.open(input);
        clip.start();

    }

    public void mouseEntered(MouseEvent arg0) { }

    public void mouseExited(MouseEvent arg0) { }

    public void mousePressed(MouseEvent arg0) { }

    public void mouseReleased(MouseEvent arg0) { }
}
