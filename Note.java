import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Note extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JPanel topPanel;
    private ArrayList<Document> documents = new ArrayList<>();
    private Document currentDocument;
    public Note() {
        textArea = new JTextArea();
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        currentDocument = new Document("Untitled");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JTextField titleOfDocumentTextField = new JTextField(currentDocument.getTitle());
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        openItem.addActionListener(this);
        fileMenu.add(newItem);
        newItem.addActionListener(this);
        fileMenu.add(saveItem);
        saveItem.addActionListener(this);
        fileMenu.add(exitItem);
        exitItem.addActionListener(this);
        menuBar.add(fileMenu);
        menuBar.add(new JLabel("Title: "));
        menuBar.add(titleOfDocumentTextField, BorderLayout.EAST);
        setJMenuBar(menuBar);
        
        currentDocument.setContent(textArea.getText());
                
        setTitle("Note");
        setSize(800, 1100);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Open":
                JFrame openDocumentsFrame = new JFrame("Open Document");
                openDocumentsFrame.setSize(400, 300);
                JList <Document> documentList = new JList<>(documents.toArray(new Document[0]));
                documentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                documentList.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        if (evt.getClickCount() == 2) {
                            int index = documentList.locationToIndex(evt.getPoint());
                            if (index >= 0) {
                                currentDocument = documents.get(index);
                                textArea.setText(currentDocument.getContent());
                                openDocumentsFrame.dispose();
                            }
                        }
                    }
                });
                JScrollPane scrollPane = new JScrollPane(documentList);

                openDocumentsFrame.add(scrollPane);
                openDocumentsFrame.setLocationRelativeTo(this);
                openDocumentsFrame.setVisible(true);
                break;

            case "New":
                if (currentDocument.isModified()){}
            case "Save":
                String title = currentDocument.getTitle();
                if (!title.isEmpty()) {
                    currentDocument.setTitle(title);
                    currentDocument.setContent(textArea.getText());
                    currentDocument.save();
                    documents.add(currentDocument);
                    JOptionPane.showMessageDialog(this, "Document saved as " + title, "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "File name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
    public boolean checkExistingDocument() {
        boolean exists = false;
        for (Document doc : documents){
            if (currentDocument.getTitle().equals(doc.getTitle())) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    public static void run() {
        new Note();
    }
}

class Document {
    private String content;
    private String title;
    private boolean isModified;

    public Document(String title) {
        this.title = title;
        this.content = "";
        this.isModified = false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.isModified = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isModified() {
        return isModified;
    }

    public void save() {
        isModified = false;
    }

    @Override
    public String toString() {
        return title + (isModified ? " *" : "");
    }
}
