package conver_text_to_speech;

import javax.swing.*;
import java.awt.*;

public class Textarea extends JTextArea {

    private String placeholder;

    public Textarea(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !hasFocus()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.gray);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left + 5, insets.top + g2.getFontMetrics().getAscent());
            g2.dispose();
        }
    }
}
