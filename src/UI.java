import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class UI extends JFrame implements ActionListener {

    private final String[] messages = {"Exercise Time:", "Break time:", "Rounds before larger break:",
            "Large brake time:", "Total rounds:"};
    private JTextField[] InputFields = new JTextField[messages.length];
    private JButton openButton;
    private JFileChooser fileChooser;
    private JTextArea chosenFileText;
    private File chosenFile;
    private final File config = new File("config.txt");

    private final FileNameExtensionFilter audioFilter = new FileNameExtensionFilter("Audio Files",
            "mp3", "wav");

    public UI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 2000);
        setLocation(0, 0);
        setupConfigFile();
        setupFields();
        setupFileChooser();
        setLayout(null);
        setVisible(true);
    }

    private void setupConfigFile() {
        if (config.exists()) {
            try {
                String s = Files.readString(Path.of(config.getPath()));
                chosenFile = new File(s);
            }
            catch (IOException e) {
                System.err.println("reading of config file failed");
                e.printStackTrace();
            }
        }
        else {
            try {
                config.createNewFile();
            }
            catch (IOException e) {
                System.err.println("config file creation failed");
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
        chosenFileText.setBounds(450, 60, 300, 32);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(audioFilter);
        openButton = new JButton("Open File selection:");
        openButton.addActionListener(this);
        openButton.setBounds(450, 30, 200, 30);
        add(openButton);
        add(chosenFileText);
    }

    private void setupFields() {

        for (int i = 0; i < messages.length; i++) {
            JLabel label = new JLabel(messages[i]);
            JTextField field = new JTextField(i + "-tes Eingabefeld");
            field.setBounds(200, 30 * (i + 1), 200, 16);
            label.setBounds(0, 30 * (i + 1), 200, 16);
            add(label);
            add(field);
            InputFields[i] = field;
        }
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
                    Files.writeString(Path.of(config.getPath()), chosenFile.getAbsolutePath());
                }
                catch (IOException ioException) {
                    System.err.println("Saving file in config failed");
                    ioException.printStackTrace();
                }
            }
        }
    }
}
