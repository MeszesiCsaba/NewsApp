/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.ui;

import ro.umfst.newsapp.News;
import ro.umfst.newsapp.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import ro.umfst.newsapp.rss.RSSConfigReader;
import ro.umfst.newsapp.rss.RSSSource;
import ro.umfst.newsapp.rss.RSSHttpClient;
import ro.umfst.newsapp.rss.RSSParser;

import javax.swing.SwingWorker;
import java.util.ArrayList;
/**
 *
 * @author mcsab
 */
public class MainWindow extends JFrame {
    
    private JList<News> newsList;
    private JTextArea newsDetails;
    private DefaultListModel<News> listModel;
    private JTextField searchField;
    private List<News> allNews;
    private JButton refreshButton;
    private JLabel statusLabel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    
    public MainWindow(){
        
        setTitle("News Manager");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create the news list.
        listModel = new DefaultListModel<>();
        newsList = new JList<>(listModel);

        // Show only the title of each news article in the list.
        newsList.setCellRenderer((list, news, index, selected, focus) -> {
            JLabel label = new JLabel(news.getTitle());
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            if (selected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }

            return label;
        });

        JScrollPane listScrollPane = new JScrollPane(newsList);

        // Create the news details area.
        newsDetails = new JTextArea();
        newsDetails.setEditable(false);
        newsDetails.setLineWrap(true);
        newsDetails.setWrapStyleWord(true);
        newsDetails.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JScrollPane detailsScrollPane = new JScrollPane(newsDetails);

        // Display the selected article's details.
        newsList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                News selectedNews = newsList.getSelectedValue();

                if (selectedNews != null) {
                    newsDetails.setText(
                            "Title: " + selectedNews.getTitle()
                            + "\n\nDescription: "
                            + selectedNews.getDescription()
                            + "\n\nSource: " + selectedNews.getSource()
                            + "\nAuthor: " + selectedNews.getAuthor()
                            + "\nLanguage: " + selectedNews.getLanguage()
                            + "\nURL: " + selectedNews.getURL()
                    );
                    newsDetails.setCaretPosition(0);
                }
            }
        });

        // Place the list and details side by side.
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane,
                detailsScrollPane
        );

        splitPane.setDividerLocation(330);
        add(splitPane, BorderLayout.CENTER);
            
        // Create buttons for managing news.
        JPanel buttonPanel = new JPanel();

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // Connect buttons to their actions.
        addButton.addActionListener(e -> addNews());
        editButton.addActionListener(e -> editNews());
        deleteButton.addActionListener(e -> deleteNews());

        add(buttonPanel, BorderLayout.SOUTH);
        
        // Create the search field.
        searchField = new JTextField(25);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Create the refresh button.
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshNews());
        searchPanel.add(refreshButton, BorderLayout.EAST);

        // Show the current status.
        statusLabel = new JLabel("Ready");
        searchPanel.add(statusLabel, BorderLayout.SOUTH);

        add(searchPanel, BorderLayout.NORTH);
        // Filter news while the user types.
        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e) {
                filterNews();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e) {
                filterNews();
            }

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e) {
                filterNews();
            }
        });


        // Load saved news from the database.
        loadNews();
    }

    /**
     * Loads all saved news articles from the database.
     */
    
    private void loadNews() {
        try {
            allNews = DatabaseManager.getAllNews();

            listModel.clear();

            for (News item : allNews) {
                listModel.addElement(item);
            }

            setTitle(
                    "News and Events Manager - "
                    + allNews.size() + " articles"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load news: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
     /**
     * Filters news by title or description.
     */
    private void filterNews() {
        if (allNews == null) {
            return;
        }

        String searchText = searchField.getText()
                .trim()
                .toLowerCase();

        listModel.clear();

        for (News item : allNews) {
            String title = item.getTitle() == null
                    ? ""
                    : item.getTitle().toLowerCase();

            String description = item.getDescription() == null
                    ? ""
                    : item.getDescription().toLowerCase();

            if (title.contains(searchText)
                    || description.contains(searchText)) {
                listModel.addElement(item);
            }
        }

        newsDetails.setText("");
    }
    
    /**
     * Downloads RSS feeds in the background and saves new articles.
     */
    private void refreshNews() {
        refreshButton.setEnabled(false);
        statusLabel.setText("Refreshing news...");

        SwingWorker<Integer, Void> worker =
                new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                int savedCount = 0;

                List<RSSSource> sources =
                        RSSConfigReader.readSources();

                for (RSSSource source : sources) {
                    try {
                        String content =
                                RSSHttpClient.getContent(source.getURL());

                        String language =
                                RSSParser.getLanguage(content);

                        if (language == null) {
                            continue;
                        }

                        List<News> parsedNews =
                                RSSParser.parseNews(
                                        content,
                                        source.getName(),
                                        language
                                );

                        for (News news : parsedNews) {
                            News existingNews =
                                    DatabaseManager.getNews(
                                            news.getSource(),
                                            news.getURL()
                                    );

                            if (existingNews == null) {
                                DatabaseManager.addNews(news);
                                savedCount++;
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(
                                "Could not process "
                                + source.getName()
                                + ": " + e.getMessage()
                        );
                    }
                }

                return savedCount;
            }

            @Override
            protected void done() {
                try {
                    int savedCount = get();

                    loadNews();
                    filterNews();

                    statusLabel.setText(
                            "Refresh complete. New articles: "
                            + savedCount
                    );

                } catch (Exception e) {
                    statusLabel.setText("Refresh failed.");

                    JOptionPane.showMessageDialog(
                            MainWindow.this,
                            "Could not refresh news: "
                            + e.getMessage(),
                            "RSS Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {
                    refreshButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }
    
    
    /**
     * Opens a dialog to add a new news article.
     */
    private void addNews() {
        JTextField titleField = new JTextField();
        JTextField sourceField = new JTextField();
        JTextField urlField = new JTextField();
        JTextArea descriptionArea = new JTextArea(5, 20);

        Object[] fields = {
            "Title:", titleField,
            "Source:", sourceField,
            "URL:", urlField,
            "Description:", new JScrollPane(descriptionArea)
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add News",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();
                String source = sourceField.getText().trim();
                String url = urlField.getText().trim();
                String description = descriptionArea.getText().trim();

                if (title.isEmpty() || source.isEmpty() || url.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Title, source and URL are required."
                    );
                    return;
                }

                News news = new News(
                            title,                  // title
                        description,            // description
                        source,                 // source
                        null,                   // author
                        source,                 // publisher
                        null,                   // publishdate
                        url,                    // url
                        new java.sql.Timestamp(System.currentTimeMillis()), // creationdate
                        new java.sql.Timestamp(System.currentTimeMillis()), // lastedit
                        "RO",                   // language
                        "res/images/default.jpg" // coverimage
                );

                DatabaseManager.addNews(news);
                loadNews();

                JOptionPane.showMessageDialog(
                        this,
                        "News added successfully."
                );

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not add news: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    
    /**
     * Edits the selected news article.
     */
    private void editNews() {
        News selectedNews = newsList.getSelectedValue();

        if (selectedNews == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a news article first."
            );
            return;
        }

        JTextField titleField =
                new JTextField(selectedNews.getTitle());
        JTextField authorField =
                new JTextField(selectedNews.getAuthor() == null
                        ? "" : selectedNews.getAuthor());
        JTextArea descriptionArea =
                new JTextArea(
                        selectedNews.getDescription() == null
                                ? "" : selectedNews.getDescription(),
                        5, 20
                );

        Object[] fields = {
            "Title:", titleField,
            "Author:", authorField,
            "Description:", new JScrollPane(descriptionArea)
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Edit News",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();

                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Title cannot be empty."
                    );
                    return;
                }

                selectedNews.setTitle(title);
                selectedNews.setAuthor(authorField.getText().trim());
                selectedNews.setDescription(
                        descriptionArea.getText().trim()
                );
                selectedNews.setLastEdit(
                        new java.sql.Timestamp(
                                System.currentTimeMillis()
                        )
                );

                DatabaseManager.updateNews(selectedNews);
                loadNews();

                JOptionPane.showMessageDialog(
                        this,
                        "News updated successfully."
                );

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not update news: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    /**
     * Deletes the selected news article after confirmation.
     */
    private void deleteNews() {
        News selectedNews = newsList.getSelectedValue();

        if (selectedNews == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a news article first."
            );
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this article?\n"
                + selectedNews.getTitle(),
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                DatabaseManager.deleteNews(
                        selectedNews.getSource(),
                        selectedNews.getURL()
                );

                loadNews();
                newsDetails.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "News deleted successfully."
                );

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not delete news: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

}
