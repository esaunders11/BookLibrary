import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;



public class LibraryGUI {

    private Library library;
    private DefaultTableModel tableModel;

    public LibraryGUI() {
        initialize();
    }

    public void initialize() {
        JFrame frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null);

        library = new Library();

        String[] columns = {"Title", "Author", "Genre", "Number of Pages"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable bookTable = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(bookTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        updateTable();

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = bookTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String title = (String) tableModel.getValueAt(selectedRow, 0);
                        String author = (String) tableModel.getValueAt(selectedRow, 1);
                        Book book = library.getBook(title, author);
                        
                        
                        showBookInfo(book);
                    }
                }
            }
        });
        

        JTableHeader header = bookTable.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = header.columnAtPoint(e.getPoint());
                System.out.print(col);
                if (col == 0) {
                    Library.sortBooksTitle(library);
                    updateTable();
                } 
                else if (col == 1) {
                    Library.sortBooksAuthor(library);
                    updateTable();
                } 
                else if (col == 2) {
                    Library.sortBooksGenre(library);
                    updateTable();
                } 
                else if (col == 3) {
                    Library.sortBooksLength(library);
                    updateTable();
                }
            }
        });

        
        JButton removeButton = new JButton("Remove Book");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow >= 0) {
                    library.removeBook(selectedRow);  
                    updateTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a book to remove.");
                }
            }
        });

        
        JPanel addBookPanel = new JPanel(new GridLayout(5, 2));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField lengthField = new JTextField();
        JButton addButton = new JButton("Add Book");

        addBookPanel.add(new JLabel("Title:"));
        addBookPanel.add(titleField);
        addBookPanel.add(new JLabel("Author:"));
        addBookPanel.add(authorField);
        addBookPanel.add(new JLabel("Genre:"));
        addBookPanel.add(genreField);
        addBookPanel.add(new JLabel("Number of Pages:"));
        addBookPanel.add(lengthField);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                String lengthText = lengthField.getText();

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || lengthText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int length;
                try {
                    length = Integer.parseInt(lengthText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Length must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Book newBook = new Book(title, author, genre, length);
                    String[] info = BookAPI.searchBook(newBook.getTitle());
                    newBook.addInfo(info);
                    library.addBook(newBook);  
                    updateTable();
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(frame, "Invalid Genre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                titleField.setText("");
                authorField.setText("");
                genreField.setText("");
                lengthField.setText("");
            }
        });

        addBookPanel.add(addButton);

        // Reset library button
        JButton resetButton = new JButton("Reset Library");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.resetLibrary();  
                updateTable();
            }
        });

        
        JButton loadButton = new JButton("Load Library");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    library.addLibrary(selectedFile.getAbsolutePath()); 
                }
                updateTable();
            }
        });

    
        JButton exportButton = new JButton("Export Library");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame exportFrame = new JFrame("Export Preview");
                exportFrame.setSize(400, 300);
                exportFrame.setLayout(new BorderLayout());

                exportFrame.setLocationRelativeTo(null);

                JTextArea exportPreview = new JTextArea(libraryPreview());
                exportFrame.add(new JScrollPane(exportPreview), BorderLayout.CENTER);

                JButton confirmExportButton = new JButton("Export");
                confirmExportButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser exportFileChooser = new JFileChooser();
                        int exportResult = exportFileChooser.showSaveDialog(exportFrame);
                        if (exportResult == JFileChooser.APPROVE_OPTION) {
                            File file = exportFileChooser.getSelectedFile();
                            try {
                                library.exportLibrary(file.getAbsolutePath());
                                JOptionPane.showMessageDialog(exportFrame, "Library exported successfully!");
                                exportFrame.dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Failed to export the file. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                exportFrame.add(confirmExportButton, BorderLayout.SOUTH);
                exportFrame.setVisible(true);
            }
        });

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(removeButton);
        bottomPanel.add(resetButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(exportButton);

        frame.add(addBookPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showBookInfo(Book b) {
        
        JFrame bookInfoDialog = new JFrame();
        bookInfoDialog.setTitle("Book Information");
        bookInfoDialog.setSize(300, 200);
        bookInfoDialog.setLayout(new GridLayout(5, 1));

        
        bookInfoDialog.add(new JLabel("Info:"));
        try {
        bookInfoDialog.add(new JLabel(b.getInfo()[0]));
        if (b.getInfo()[1] == null) {
            bookInfoDialog.add(new JLabel("Ain't work dawg."));
        } else{
            bookInfoDialog.add(new JLabel(b.getInfo()[1]));
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(bookInfoDialog, "Failed to find info.");
        }
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookInfoDialog.dispose(); 
            }
        });

        bookInfoDialog.add(closeButton);

        
        bookInfoDialog.setLocationRelativeTo(null);
        bookInfoDialog.setVisible(true);
    }


    private void updateTable() {
        tableModel.setRowCount(0); 
        String[][] books = library.getBookString();
        for (String[] book : books) {
            if (book != null) {
                tableModel.addRow(book);
            }
        }
    }

    private String libraryPreview() {
        StringBuilder preview = new StringBuilder();
        String[][] books = library.getBookString();
        for (String[] book : books) {
            if (book != null) {
                preview.append(book[0]).append(",").append(book[1])
                        .append(",").append(book[2]).append(",")
                        .append(book[3]).append("\n");
            }
        }
        return preview.toString();
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
