package com.textEditor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontBox;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    public TextEditor() {
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Simple Text Editor");
        setSize(600, 400);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(16);
        fontSizeSpinner.addChangeListener(e -> textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue())));

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(e -> textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize())));

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = createMenuItem("Open");
        saveItem = createMenuItem("Save");
        exitItem = createMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Font Size:"));
        topPanel.add(fontSizeSpinner);
        topPanel.add(new JLabel("Font:"));
        topPanel.add(fontBox);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JMenuItem createMenuItem(String name) {
        return new JMenuItem(name);
    }

    private void performOpenAction() {
        JFileChooser fileChooser = getFileChooser("Open", "Text files", "txt");

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            readFile(file);
        }
    }

    private void performSaveAction() {
        JFileChooser fileChooser = getFileChooser("Save", null, null);

        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            writeFile(file);
        }
    }

    private void readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.read(reader, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            textArea.write(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JFileChooser getFileChooser(String dialogTitle, String fileDescription, String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileDescription != null && fileExtension != null) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(fileDescription, fileExtension);
            fileChooser.setFileFilter(filter);
        }
        fileChooser.setDialogTitle(dialogTitle);
        return fileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openItem) {
            performOpenAction();
        } else if (e.getSource() == saveItem) {
            performSaveAction();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
