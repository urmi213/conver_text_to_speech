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
    private WaveformPanel waveformPanel;
    private Timer animationTimer;

    public TextToSpeech() {
        setTitle("Text To Speech");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        // Textarea
        inputText = new Textarea("Enter your text here...");
        inputText.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        inputText.setBackground(new Color(255, 255, 255, 230));
        inputText.setBounds(0, 0, 340, 72);  

        //  Added Scroll
        JScrollPane scrollPane = new JScrollPane(inputText);
        scrollPane.setBounds(169, 225, 340, 72);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        
        
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        backgroundPanel.add(scrollPane);

        try {
            BufferedImage topMicImg = ImageIO.read(getClass().getResource("mics.png"));
            topMicButton = new JButton(new ImageIcon(topMicImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            topMicButton.setBounds(413, 98, 60, 60);
            topMicButton.setContentAreaFilled(false);
            topMicButton.setBorderPainted(false);
            topMicButton.setFocusPainted(false);
            topMicButton.setOpaque(false);
            topMicButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            backgroundPanel.add(topMicButton);

            waveformPanel = new WaveformPanel();
            waveformPanel.setBounds(475, 98, 200, 60);
            waveformPanel.setOpaque(false);
            backgroundPanel.add(waveformPanel);

            animationTimer = new Timer(100, e -> waveformPanel.repaint());

            BufferedImage leftMicImg = ImageIO.read(getClass().getResource("img.jpeg"));
            leftMicButton = new JButton(new ImageIcon(leftMicImg.getScaledInstance(105, 120, Image.SCALE_SMOOTH)));
            leftMicButton.setBounds(68, 320, 105, 120);
            
            leftMicButton.setOpaque(true);
            leftMicButton.setContentAreaFilled(true);
            leftMicButton.setBackground(new Color(200, 200, 255));
            leftMicButton.setFocusPainted(false);
            leftMicButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            leftMicButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            leftMicButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    leftMicButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
                    leftMicButton.setBackground(new Color(180, 180, 255));
                }
                @Override
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    leftMicButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                    leftMicButton.setBackground(new Color(200, 200, 255));
                }
            });

            leftMicButton.addActionListener(e -> {
                String text = inputText.getText().trim();
                if (!text.isEmpty()) {
                    animationTimer.start();
                    new Thread(() -> {
                        speak(text);
                        animationTimer.stop();
                        waveformPanel.repaint();
                    }).start();
                } else {
                    JOptionPane.showMessageDialog(TextToSpeech.this, "Please enter text.");
                }
            });

            backgroundPanel.add(leftMicButton);

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

    class WaveformPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(255, 69, 0, 90));

            int width = getWidth();
            int height = getHeight();
            int spacing = 14;
            int barWidth = 2;
            int bars = width / spacing;

            for (int i = 0; i < bars; i++) {
                int barHeight = (int) (Math.random() * height);
                int x = i * spacing;
                int y = height - barHeight;
                g2d.fillRect(x, y, barWidth, barHeight);
            }
            g2d.dispose();
        }
    }

    class Background extends JPanel {
        private final Image backgroundImage;
        public Background(Image img) {
            this.backgroundImage = img;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

}
