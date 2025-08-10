package conver_text_to_speech;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech extends JFrame {

    private Textarea inputText;
    private JButton topMicButton;
    private JButton leftMicButton;

    public TextToSpeech() {
        setTitle("Text To Speech");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("background.png"));
        } catch (IOException e) {
            System.out.println("Background image not found!");
        }

        Background backgroundPanel = new Background(backgroundImage);
        backgroundPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Text To Speech");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBounds(370, 30, 400, 40);
        backgroundPanel.add(titleLabel);

        // Text input
        inputText = new Textarea("Enter your text here...");
        inputText.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        inputText.setBackground(new Color(255, 255, 255, 230));
        inputText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        inputText.setBounds(169, 225, 340, 72);
        backgroundPanel.add(inputText);

        try {
            // Top mic button (mics.png)
            BufferedImage topMicImg = ImageIO.read(getClass().getResource("mics.png"));
            topMicButton = new JButton(new ImageIcon(topMicImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            topMicButton.setBounds(413, 98, 60, 60);
            topMicButton.setContentAreaFilled(false);
            topMicButton.setBorderPainted(false);
            topMicButton.setFocusPainted(false);
            topMicButton.setOpaque(false);
            topMicButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backgroundPanel.add(topMicButton);

            // Left 
            BufferedImage leftMicImg = ImageIO.read(getClass().getResource("img.jpeg"));
            leftMicButton = new JButton(new ImageIcon(leftMicImg.getScaledInstance(105, 120, Image.SCALE_SMOOTH)));
            leftMicButton.setBounds(68, 320, 105, 120);
            leftMicButton.setContentAreaFilled(false);
            leftMicButton.setBorderPainted(false);
            leftMicButton.setFocusPainted(false);
            leftMicButton.setOpaque(false);
            leftMicButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backgroundPanel.add(leftMicButton);

            // Common action for both buttons
            Action speakAction = new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String text = inputText.getText().trim();
                    if (!text.isEmpty()) {
                        speak(text);
                    } else {
                        JOptionPane.showMessageDialog(TextToSpeech.this, "Please enter text.");
                    }
                }
            };

            // Apply action to both buttons
            topMicButton.addActionListener(speakAction);
            leftMicButton.addActionListener(speakAction);

        } catch (IOException e) {
            System.out.println("Mic images not found!");
        }

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void speak(String text) {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        Voice voice = VoiceManager.getInstance().getVoice("kevin16");

        if (voice != null) {
            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        } else {
            System.out.println("Voice not found.");
        }
    }

    class Background extends JPanel {
        private final Image backgroundImage;

        public Background(Image img) {
            this.backgroundImage = img;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
