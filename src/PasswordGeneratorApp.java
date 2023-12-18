import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PasswordGeneratorApp extends JFrame {
    JLabel passwordLengthLabel, newPasswordLabel;
    JSpinner passwordLengthSpinner;
    JCheckBox lowerCaseBox, upperCaseBox, numbersBox, specialCharacterBox;
    JButton generateButton, copyButton;
    JTextField newPasswordEntry;

    public PasswordGeneratorApp(){
        setTitle("Password Generator");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // content pane
        JPanel contentPane = new JPanel(new GridLayout(5, 1));

        // Panel 1
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        // password length label
        passwordLengthLabel = new JLabel("Password Length:");
        panel1.add(passwordLengthLabel);
        // password length spinner
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(4, 4, 16,1);
        passwordLengthSpinner = new JSpinner(spinnerNumberModel);
        panel1.add(passwordLengthSpinner);
        contentPane.add(panel1);

        // Panel 2
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 2));
        // lower case checkbox
        lowerCaseBox = new JCheckBox("Lowercase Letters");
        panel2.add(lowerCaseBox);
        // upper case checkbox
        upperCaseBox = new JCheckBox("Uppercase Letters");
        panel2.add(upperCaseBox);
        // numbers checkbox
        numbersBox = new JCheckBox("Numbers");
        panel2.add(numbersBox);
        // lower case checkbox
        specialCharacterBox = new JCheckBox("Special Characters");
        panel2.add(specialCharacterBox);
        contentPane.add(panel2);

        // panel 3
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        // generate button
        generateButton = new JButton("Generate");
        panel3.add(generateButton);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int passwordLength = (int) passwordLengthSpinner.getValue();
                String newPassword = generatePassword(passwordLength);
                newPasswordEntry.setText(newPassword);
            }
        });
        contentPane.add(panel3);

        // panel 4
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1, 2));
        // new password label
        newPasswordLabel = new JLabel("New Password is:");
        panel4.add(newPasswordLabel);
        // new password entry
        newPasswordEntry = new JTextField();
        panel4.add(newPasswordEntry);
        contentPane.add(panel4);

        // panel 5
        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout());
        // generate button
        copyButton = new JButton("Copy");
        panel5.add(copyButton);
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = newPasswordEntry.getText();
                copyToClipboard(textToCopy);
                JOptionPane.showMessageDialog(null, "Text copied");
            }
        });
        contentPane.add(panel5);


        add(contentPane);
        pack();
    }

    private void copyToClipboard(String text){
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private String generatePassword(int length){
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        String availableChars = "";
        try{
            if (lowerCaseBox.isSelected()) availableChars += lowerCaseChars;
            if (upperCaseBox.isSelected()) availableChars += upperCaseChars;
            if (numbersBox.isSelected()) availableChars += numberChars;
            if (specialCharacterBox.isSelected()) availableChars += specialChars;
        }
        catch (Exception e){
            e.printStackTrace();
            if (availableChars.isEmpty())
                JOptionPane.showMessageDialog(PasswordGeneratorApp.this, "You must tick a box");
        }


        if (availableChars.isEmpty()){
            JOptionPane.showMessageDialog(PasswordGeneratorApp.this, "You must tick a box");
            return "";
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++){
            int randomIndex = random.nextInt(availableChars.length());
            password.append(availableChars.charAt(randomIndex));
        }
        return password.toString();

    }

}
