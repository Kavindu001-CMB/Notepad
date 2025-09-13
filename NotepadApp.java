import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadApp extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    public NotepadApp() {
        // Set up the frame
        setTitle("Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        fileChooser = new JFileChooser(System.getProperty("user.home")); // Default to user's home directory

        // Create Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveFile());

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add menu items to file menu
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Add components to frame
        add(buttonPanel, BorderLayout.NORTH); // Button at top
        add(scrollPane, BorderLayout.CENTER); // Text area in center

        // Add action listeners
        newMenuItem.addActionListener(e -> newFile());
        openMenuItem.addActionListener(e -> openFile());
        saveMenuItem.addActionListener(e -> saveFile());
        saveAsMenuItem.addActionListener(e -> saveAsFile());
        exitMenuItem.addActionListener(e -> System.exit(0));

        // Window listener for unsaved changes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isTextChanged()) {
                    int option = JOptionPane.showConfirmDialog(
                        NotepadApp.this,
                        "Do you want to save changes before exiting?",
                        "Save Changes",
                        JOptionPane.YES_NO_CANCEL_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        saveFile();
                    } else if (option == JOptionPane.CANCEL_OPTION) {
                        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        return;
                    }
                }
                System.exit(0);
            }
        });
    }

    private void newFile() {
        if (isTextChanged()) {
            int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save changes before creating a new file?",
                "Save Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        textArea.setText("");
        currentFile = null;
        setTitle("Notepad");
    }

    private void openFile() {
        if (isTextChanged()) {
            int option = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save changes before opening another file?",
                "Save Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                setTitle("Notepad - " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveAsFile();
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(textArea.getText());
                setTitle("Notepad - " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAsFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            if (!currentFile.getName().endsWith(".txt")) {
                currentFile = new File(currentFile.getPath() + ".txt");
            }
            saveFile();
        }
    }

    private boolean isTextChanged() {
        // Simple check for changes (could be enhanced with document listener)
        return !textArea.getText().isEmpty() || currentFile != null;
    }

    public static void main(String[] args) {
        // Check for headless environment
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Error: This application requires a graphical environment.");
            System.exit(1);
        }
        SwingUtilities.invokeLater(() -> {
            new NotepadApp().setVisible(true);
        });
    }
}