package com.example.traveldb;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.ArrayList;
public class MainFrame extends JFrame {

    private DatabaseManager dbManager;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JTextArea resultsTextArea;

    private JPanel dbManagementPanel;
    private JButton createDBButton;
    private JButton dropDBButton;
    private JButton connectToDBButton;
    private JTextField destinationField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField transportField;
    private JTextField tripIdField;  // Добавлено поле для ID поездки (для update и delete)
    private JPanel dataPanel;
    private JButton createTripsTableButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField searchField;
    private JButton backToDBManagementButton;

    public MainFrame() {
        setTitle("Travel Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Создание панелей
        createLoginPanel();
        createDBManagementPanel();
        createDataPanel();

        // Скрытие панелей
        dbManagementPanel.setVisible(false);
        dataPanel.setVisible(false);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(loginPanel, "login");
        mainPanel.add(dbManagementPanel, "dbManagement");
        mainPanel.add(dataPanel, "dataPanel");
        add(mainPanel);

        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(new String[]{"admin", "guest"});
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String selectedRole = (String) roleComboBox.getSelectedItem();

                try {
                	
                    dbManager = new DatabaseManager(username, password, selectedRole);
                    
                    dbManager.connectToPostgres();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Login successful as " + dbManager.getCurrentUserRole(),
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    CardLayout cl = (CardLayout) mainPanel.getLayout();
                    cl.show(mainPanel, "dbManagement");
                    dbManagementPanel.setVisible(true);
                    loginPanel.setVisible(false);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Login failed: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("Role:"));
        loginPanel.add(roleComboBox);
        loginPanel.add(loginButton);
    }

    private void createDBManagementPanel() {
        dbManagementPanel = new JPanel();
        dbManagementPanel.setLayout(new FlowLayout());

        createDBButton = new JButton("Create TravelDB");
        dropDBButton = new JButton("Drop TravelDB");
        connectToDBButton = new JButton("Connect to TravelDB (Guest)");

        createDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbManager.createDatabase();
                    resultsTextArea.setText("Database 'travel_db' created successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error creating database: You don't have enough permissions. ",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dropDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbManager.dropDatabase();
                    resultsTextArea.setText("Database 'travel_db' dropped successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error dropping database: You don't have enough permissions.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        connectToDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbManager.connectToTravel();
                    resultsTextArea.setText("Connected to database 'travel_db'");
                    CardLayout layout = (CardLayout) mainPanel.getLayout();
                    layout.show(mainPanel, "dataPanel");
                    dbManagementPanel.setVisible(false);
                    dataPanel.setVisible(true);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error connecting to database: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dbManagementPanel.add(new JLabel("Database Management:"));
        dbManagementPanel.add(createDBButton);
        dbManagementPanel.add(dropDBButton);
        dbManagementPanel.add(connectToDBButton);
    }

    private void createDataPanel() {
        dataPanel = new JPanel();
        dataPanel.setLayout(new FlowLayout());

        createTripsTableButton = new JButton("Create Trips Table");

 
        destinationField = new JTextField(15);
        startDateField = new JTextField(8);  
        endDateField = new JTextField(8);   
        transportField = new JTextField(10);
        tripIdField = new JTextField(5); 
        resultsTextArea = new JTextArea(10, 40);
        resultsTextArea.setEditable(false);

        insertButton = new JButton("Insert Data");
        updateButton = new JButton("Update Data");
        deleteButton = new JButton("Delete Data");
        searchButton = new JButton("Search");
        searchField = new JTextField(15);

        backToDBManagementButton = new JButton("Back to DB Management");
        backToDBManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) mainPanel.getLayout();
                layout.show(mainPanel, "dbManagement");
                dataPanel.setVisible(false);
                dbManagementPanel.setVisible(true);
            }
        });

        createTripsTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbManager.createTripsTable();
                    resultsTextArea.setText("Trips Table created successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error creating trips table: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String destination = destinationField.getText();
                    String startDateString = startDateField.getText();
                    String endDateString = endDateField.getText();
                    String transport = transportField.getText();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate = new Date(dateFormat.parse(startDateString).getTime());
                        endDate = new Date(dateFormat.parse(endDateString).getTime());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Invalid date format. Please use yyyy-MM-dd.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dbManager.insertData(destination, startDate, endDate, transport);
                    resultsTextArea.setText("Data inserted successfully.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error inserting data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tripId = Integer.parseInt(tripIdField.getText()); // Get tripId from field
                    String destination = destinationField.getText();
                    String startDateString = startDateField.getText();
                    String endDateString = endDateField.getText();
                    String transport = transportField.getText();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate = new Date(dateFormat.parse(startDateString).getTime());
                        endDate = new Date(dateFormat.parse(endDateString).getTime());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Invalid date format. Please use yyyy-MM-dd.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dbManager.updateData(tripId, destination, startDate, endDate, transport); //Pass TripID
                    resultsTextArea.setText("Data updated successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Invalid Trip ID.  Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error updating data: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String destination = destinationField.getText();

                    dbManager.deleteData(destination);
                    resultsTextArea.setText("Data deleted successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Invalid Destination. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error deleting data: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String destination = searchField.getText();
                try {
                    List<Trip> trips = dbManager.searchByDestination(destination);
                    StringBuilder sb = new StringBuilder();
                    for (Trip trip : trips) {
                        sb.append(trip.toString()).append("\n");
                    }
                    resultsTextArea.setText(sb.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error searching: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel searchLabel = new JLabel("Destination:");
        searchField = new JTextField(15);

        dataPanel.add(new JLabel("Destination:"));   // Добавляем метки к полям ввода
        dataPanel.add(destinationField);
        dataPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        dataPanel.add(startDateField);
        dataPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        dataPanel.add(endDateField);
        dataPanel.add(new JLabel("Transport:"));
        dataPanel.add(transportField);
        dataPanel.add(new JLabel("Trip ID:"));  //Метка для ID поездки
        dataPanel.add(tripIdField);
        dataPanel.add(new JLabel("Data Operations:"));

        dataPanel.add(createTripsTableButton);
        dataPanel.add(insertButton);
        dataPanel.add(updateButton);
        dataPanel.add(deleteButton);
        dataPanel.add(searchLabel);
        dataPanel.add(searchField);
        dataPanel.add(searchButton);
        dataPanel.add(new JScrollPane(resultsTextArea));
        dataPanel.add(backToDBManagementButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
     });
    }
  
}