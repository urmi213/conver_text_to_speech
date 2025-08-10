package conver_text_to_speech;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        new TextToSpeech().setVisible(true);
        SwingUtilities.invokeLater(TextToSpeech::new);
    }
}
