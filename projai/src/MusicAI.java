import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MusicAI extends JFrame {
    private final String[] parameters = {
            "danceability", "energy", "key", "loudness", "mode",
            "speechiness", "acousticness", "instrumentalness",
            "liveness", "valence", "tempo", "time_signature"
    };

    private final JTextField[] inputFields = new JTextField[parameters.length];
    private final JTextField genreOutput = new JTextField();

    public MusicAI() {
        setTitle("Music Genre Classifier");
        setSize(1100, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Border panel
        JPanel borderPanel = new JPanel();
        borderPanel.setBounds(10, 10, 1060, 380);
        borderPanel.setBorder(new LineBorder(Color.BLACK, 3));
        borderPanel.setLayout(null);
        setContentPane(borderPanel);

        // Input
        int startY = 30;
        for (int i = 0; i < parameters.length; i++) {
            JLabel label = new JLabel("'" + parameters[i] + "',");
            label.setBounds(20, startY + i * 30, 120, 25);
            borderPanel.add(label);

            inputFields[i] = new JTextField();
            inputFields[i].setBounds(150, startY + i * 30, 100, 25);
            borderPanel.add(inputFields[i]);
        }

        // losuj
        JButton randomizeButton = new JButton("Losuj wartości");
        randomizeButton.setBounds(270, 60, 160, 60);
        randomizeButton.setBorder(new LineBorder(Color.BLACK, 2));
        borderPanel.add(randomizeButton);

        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                for (int i = 0; i < parameters.length; i++) {
                    switch (parameters[i]) {
                        case "key", "mode", "time_signature":
                            inputFields[i].setText(String.valueOf(rand.nextInt(12)));
                            break;
                        case "tempo":
                            inputFields[i].setText(String.format("%.2f", 60 + rand.nextDouble() * 140));
                            break;
                        case "loudness":
                            inputFields[i].setText(String.format("%.2f", -60 + rand.nextDouble() * 60));
                            break;
                        default:
                            inputFields[i].setText(String.format("%.2f", rand.nextDouble()));
                    }
                }
            }
        });

        // wygeneruj
        JButton generateButton = new JButton("Wygeneruj gatunek piosenki");
        generateButton.setBounds(270, 310, 250, 60);
        generateButton.setBorder(new LineBorder(Color.BLACK, 2));
        borderPanel.add(generateButton);

        // Output
        JLabel outputLabel = new JLabel("Utwór o podanych parametrach należy do gatunku:");
        outputLabel.setBounds(470, 60, 400, 30);
        borderPanel.add(outputLabel);

        genreOutput.setBounds(880, 60, 150, 30);
        genreOutput.setBorder(new LineBorder(Color.BLACK, 2));
        borderPanel.add(genreOutput);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicAI().setVisible(true);
        });
    }
}
