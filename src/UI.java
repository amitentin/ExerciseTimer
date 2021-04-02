import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Arrays;


public class UI extends JFrame implements ActionListener {

    private final String[] messages = {"Exercise Time:", "Break time:", "Rounds before larger break:",
            "Large break time:", "Total rounds:"};
    private JFormattedTextField[] InputFields = new JFormattedTextField[messages.length];
    private JButton openButton;
    private JFileChooser fileChooser;
    private JTextArea chosenFileText;
    private File chosenFile;
    private JButton runButton;
    private final File musicConfig = new File("musicConfig.txt");

    private final FileNameExtensionFilter audioFilter = new FileNameExtensionFilter("Audio Files",
            "mp3", "wav");

    public UI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocation(0, 0);
        setupConfigFile();
        setupFields();
        setupFileChooser();
        runButton = new JButton("Run");
        runButton.setBounds(20,200,200,50);
        runButton.addActionListener(this);
        add(runButton);
        setLayout(null);
        setVisible(true);
    }

    private void setupConfigFile() {
        if (musicConfig.exists()) {
            try {
                String s = Files.readString(Path.of(musicConfig.getPath()));
                chosenFile = new File(s);
            }
            catch (IOException e) {
                System.err.println("reading of music config file failed");
                e.printStackTrace();
            }
        }
        else {
            try {
                musicConfig.createNewFile();
            }
            catch (IOException e) {
                System.err.println("music config file creation failed");
                e.printStackTrace();
            }
        }
    }


    private void setupFileChooser() {
        chosenFileText = new JTextArea();
        chosenFileText.setEditable(false);
        if (chosenFile == null) {
            chosenFileText.setText("No File Chosen yet");
        }
        else {
            chosenFileText.setText("Selected file:" + System.lineSeparator() + chosenFile.getAbsolutePath());
        }
        chosenFileText.setBounds(500, 60, 300, 32);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(audioFilter);
        openButton = new JButton("Select Sound File");
        openButton.addActionListener(this);
        openButton.setBounds(500, 30, 200, 30);
        add(openButton);
        add(chosenFileText);
    }

    private void setupFields() {

        for (int i = 0; i < messages.length; i++) {
            JLabel label = new JLabel(messages[i]);
            JFormattedTextField field = new JFormattedTextField(NumberFormat.getIntegerInstance());
            field.setBounds(275, 30 * (i + 1), 200, 16);
            label.setBounds(0, 30 * (i + 1), 275, 16);
            add(label);
            add(field);
            InputFields[i] = field;
        }
    }
    public static void popUp(String message, String title){
        JOptionPane.showMessageDialog(null, message, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) {
        UI ui = new UI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fileChooser.showOpenDialog(UI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                chosenFile = fileChooser.getSelectedFile();
                chosenFileText.setText("Selected file:" + System.lineSeparator() + chosenFile.getAbsolutePath());
                try {
                    Files.writeString(Path.of(musicConfig.getPath()), chosenFile.getAbsolutePath());
                }
                catch (IOException ioException) {
                    System.err.println("Saving file in music config failed");
                    ioException.printStackTrace();
                }
            }
        }
        else if (e.getSource().equals(runButton)){
            int[] inputs = Arrays.stream(InputFields).map(jFormattedTextField -> jFormattedTextField.getText())
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Timer timer = new Timer(chosenFile);
            timer.executeExercise(inputs);
        }
    }
}
