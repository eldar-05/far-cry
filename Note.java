import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Note extends JFrame implements ActionListener {
    private JTextArea textArea;
    private ArrayList<Document> documents = new ArrayList<>();
    private Document currentDocument;
    private JTextField titleOfDocumentTextField;
    public Note() {
        textArea = new JTextArea();

        if (documents.isEmpty()) {
            currentDocument = new Document("Untitled");
        } else {
            currentDocument = documents.get(0);
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        titleOfDocumentTextField = new JTextField(currentDocument.getTitle(), 20);
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        openItem.addActionListener(this);
        fileMenu.add(newItem);
        newItem.addActionListener(this);
        fileMenu.add(saveItem);
        saveItem.addActionListener(this);
        fileMenu.add(saveAsItem);
        saveAsItem.addActionListener(this);
        fileMenu.add(exitItem);
        exitItem.addActionListener(this);
        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(new JLabel(" Title: "));
        menuBar.add(titleOfDocumentTextField);
        setJMenuBar(menuBar);

        textArea.setText(currentDocument.getContent());
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                currentDocument.setContent(textArea.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }

            @Override
            public void removeUpdate(DocumentEvent e) { update(); }

            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });

        titleOfDocumentTextField.addActionListener(evt -> {
            String newTitle = titleOfDocumentTextField.getText().trim();
;            if (checkExistingDocument(newTitle)) {
                JOptionPane.showMessageDialog(this, "A document with this title already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                titleOfDocumentTextField.setText(currentDocument.getTitle());
                return;
            } else {
                if (!newTitle.isEmpty()) {
                    currentDocument.setTitle(newTitle);
                }
            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setTitle("Note");
        setSize(800, 1100);

        setLocationRelativeTo(null);
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
                                titleOfDocumentTextField.setText(currentDocument.getTitle());
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
                if (currentDocument.isModified()){
                    int option = JOptionPane.showConfirmDialog(this, "Current document is modified. Do you want to save changes?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        saveDocumentInList(currentDocument);
                    } else if (option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                currentDocument = new Document("Untitled" + (documents.size()));
                titleOfDocumentTextField.setText(currentDocument.getTitle());
                textArea.setText("");
                break;
            case "Save":
                currentDocument.setContent(textArea.getText());
                saveDocumentInList(currentDocument);
                break;
            case "Save As":
                String newTitle = JOptionPane.showInputDialog(this, "Enter new title:", currentDocument.getTitle());
                if (newTitle != null) {
                    newTitle = newTitle.trim();
                    if (newTitle.isEmpty()) {
                        // empty title -> ignore
                        break;
                    }

                    if (checkExistingDocument(newTitle)) {
                        JOptionPane.showMessageDialog(this, "A document with this title already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        currentDocument.setTitle(newTitle);
                        titleOfDocumentTextField.setText(newTitle);
                        saveDocumentInList(currentDocument);
                    }
                }
                
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
    /**
     * Check whether a document with the provided title already exists in the list
     * excluding the current document.
     */
    public boolean checkExistingDocument(String title) {
        if (title == null || title.trim().isEmpty()) return false;
        if (documents.isEmpty()) return false;
        for (Document doc : documents) {
            if (doc != currentDocument && title.equals(doc.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public void saveDocumentInList(Document doc) {
        doc.setContent(textArea.getText());
        doc.save();
        documents.add(doc);
        JOptionPane.showMessageDialog(this, "Document saved as " + doc.getTitle(), "Info", JOptionPane.INFORMATION_MESSAGE);
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
