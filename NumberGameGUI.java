import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGameGUI extends JFrame {
    private int number; // Randomly generated number
    private int attemptsLeft = 7; // Maximum attempts
    private int score = 0; // Player's score

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGameGUI() {
        // Frame settings
        setTitle("Number Guessing Game - Dark Theme");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Apply dark theme styling
        Color backgroundColor = new Color(18, 18, 18); // Dark gray
        Color textColor = new Color(240, 240, 240); // Light gray
        Color accentColor = new Color(70, 130, 180); // Samsung blue

        // Set background and font styles for the entire frame
        getContentPane().setBackground(backgroundColor);

        // Components
        messageLabel = createStyledLabel("Guess a number between 1 and 100", textColor, accentColor, 18);
        add(messageLabel);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        guessField.setBackground(new Color(40, 40, 40)); // Input background
        guessField.setForeground(textColor); // Input text color
        add(guessField);

        JButton guessButton = new JButton("Submit Guess");
        guessButton.setBackground(accentColor);
        guessButton.setForeground(textColor);
        guessButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        guessButton.setFocusPainted(false); // Remove focus border
        add(guessButton);

        attemptsLabel = createStyledLabel("Attempts left: " + attemptsLeft, textColor, accentColor, 14);
        add(attemptsLabel);

        scoreLabel = createStyledLabel("Score: " + score, textColor, accentColor, 14);
        add(scoreLabel);

        // Generate the first number
        generateRandomNumber();

        // Button click listener
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
    }

    private JLabel createStyledLabel(String text, Color textColor, Color accentColor, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(textColor);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        return label;
    }

    private void generateRandomNumber() {
        Random random = new Random();
        number = random.nextInt(100) + 1; // Generates a number between 1 and 100
        attemptsLeft = 7; // Reset attempts
        messageLabel.setText("Guess a number between 1 and 100");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void handleGuess() {
        String guessText = guessField.getText();

        // Validate input
        try {
            int guess = Integer.parseInt(guessText);

            if (guess < 1 || guess > 100) {
                messageLabel.setText("Please enter a number between 1 and 100.");
                return;
            }

            attemptsLeft--;
            if (guess == number) {
                score++;
                messageLabel.setText("Correct! The number was " + number + ". Starting new round...");
                scoreLabel.setText("Score: " + score);
                generateRandomNumber();
            } else if (attemptsLeft == 0) {
                messageLabel.setText("Out of attempts! The number was " + number + ". Starting new round...");
                generateRandomNumber();
            } else if (guess > number) {
                messageLabel.setText("Too high! Try again.");
            } else {
                messageLabel.setText("Too low! Try again.");
            }

            attemptsLabel.setText("Attempts left: " + attemptsLeft);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input! Please enter a number.");
        }

        guessField.setText(""); // Clear the input field
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGameGUI game = new NumberGameGUI();
            game.setVisible(true);
        });
    }
}
