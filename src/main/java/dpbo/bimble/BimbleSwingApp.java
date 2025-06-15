package dpbo.bimble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BimbleSwingApp {
    private JFrame mainFrame;
    private Auth auth;
    private List<Registrasi> registrasiList;
    private List<InfoTest> infoTestList;
    private List<Schedule> scheduleList;
    private PaymentHistory paymentHistory;
    private User currentUser;

    public BimbleSwingApp() {
        auth = new Auth();
        registrasiList = new ArrayList<>();
        infoTestList = new ArrayList<>();
        scheduleList = new ArrayList<>();
        paymentHistory = new PaymentHistory();
        
        Admin admin = new Admin("admin1", "adminpass", "Admin Utama", "admin");
        auth.registerUser(admin);
        
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Bimble Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        
        showLoginPanel();
        
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void showLoginPanel() {
        mainFrame.getContentPane().removeAll();
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Login to Bimbel Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        JLabel roleLabel = new JLabel("Login as:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(roleLabel, gbc);
        
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Admin", "Student"});
        gbc.gridx = 1;
        loginPanel.add(roleCombo, gbc);
        
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(userLabel, gbc);
        
        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);
        
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(passLabel, gbc);
        
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        loginPanel.add(passField, gbc);
        
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginButton.addActionListener(e -> {
            String role = roleCombo.getSelectedItem().toString().toLowerCase();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            if (role.equals("student")) {
                String name = JOptionPane.showInputDialog(mainFrame, "Enter your name:");
                if (name != null && !name.isEmpty()) {
                    Student student = new Student(username, password, name);
                    auth.registerUser(student);
                }
            }
            
            currentUser = auth.loginUser(username, password);
            if (currentUser != null) {
                if (currentUser instanceof Admin) {
                    showAdminMenu();
                } else if (currentUser instanceof Student) {
                    showStudentMenu();
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Login failed. Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(loginButton, gbc);
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        loginPanel.add(exitButton, gbc);
        
        mainFrame.add(loginPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showAdminMenu() {
        mainFrame.getContentPane().removeAll();
        
        JPanel adminPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Admin Menu - Welcome " + currentUser.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 4, 4));
        
        JButton regButton = new JButton("Registrations Management");
        regButton.addActionListener(e -> registrations());
        buttonPanel.add(regButton);
        
        JButton testButton = new JButton("Test Management");
        testButton.addActionListener(e -> showTestManagement());
        buttonPanel.add(testButton);
        
        JButton scheduleButton = new JButton("Create Schedule");
        scheduleButton.addActionListener(e -> createSchedule());
        buttonPanel.add(scheduleButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            showLoginPanel();
        });
        buttonPanel.add(logoutButton);
        
        adminPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(adminPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    private void registrations() {
        JFrame regmFrame = new JFrame("Registrations Management");
        regmFrame.setSize(500, 300);
        regmFrame.setLayout(new GridLayout(3, 1));
        
        JButton viewRegistrastionsButton = new JButton("View All Registrations");
        viewRegistrastionsButton.addActionListener(e -> viewAllRegistrations());
        regmFrame.add(viewRegistrastionsButton);
        
        JButton confirmRegButton = new JButton("Confirm Registrations");
        confirmRegButton.addActionListener(e -> confirmRegistrationAndMarkPaid());
        regmFrame.add(confirmRegButton);
        
        JButton paymentHistoryButton = new JButton("View All Payment History");
        paymentHistoryButton.addActionListener(e -> showPaymentHistory());
        regmFrame.add(paymentHistoryButton);
        
        regmFrame.setLocationRelativeTo(mainFrame);
        regmFrame.setVisible(true);
    }

    private void viewAllRegistrations() {
        if (registrasiList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No registration data available!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JFrame regFrame = new JFrame("All Registrations");
        regFrame.setSize(600, 400);
        
        String[] columnNames = {"ID", "Name", "Birth Date", "Payment Method", "Confirmed"};
        Object[][] data = new Object[registrasiList.size()][5];
        
        for (int i = 0; i < registrasiList.size(); i++) {
            Registrasi reg = registrasiList.get(i);
            data[i][0] = reg.getIdRegistrasi();
            data[i][1] = reg.getNamaLengkap();
            data[i][2] = reg.getTanggalLahir();
            data[i][3] = reg.getMetodePembayaran();
            data[i][4] = reg.isKonfirmasi() ? "Yes" : "No";
        }
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        regFrame.add(scrollPane);
        regFrame.setLocationRelativeTo(mainFrame);
        regFrame.setVisible(true);
    }
    
    private void confirmRegistrationAndMarkPaid() {
        if (registrasiList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No current Registration!.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] registrasiOptions = registrasiList.stream()
            .map(reg -> "ID: " + reg.getIdRegistrasi() + " - " + reg.getNamaLengkap())
            .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Choose regitration to be confirm:",
            "Confirm registration",
            JOptionPane.QUESTION_MESSAGE,
            null,
            registrasiOptions,
            registrasiOptions[0]
        );

        if (selected == null || selected.trim().isEmpty()) return;

        String selectedId;
        try {
            selectedId = selected.split("ID: ")[1].split(" ")[0]; 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Registrasi selectedReg = registrasiList.stream()
            .filter(r -> r.getIdRegistrasi().equals(selectedId))
            .findFirst()
            .orElse(null);

        if (selectedReg == null) {
            JOptionPane.showMessageDialog(mainFrame, "Registration data not found!!!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Payment payment = paymentHistory.getAllPayments().stream()
            .filter(p -> p.getIdRegistrasi().equals(selectedId))
            .findFirst()
            .orElse(null);

        if (payment == null) {
            JOptionPane.showMessageDialog(mainFrame, "There is no payment for this registration yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            mainFrame,
            "Confirm registration and change payment status to 'Successful'?",
            "Admin confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            payment.setStatus("Succeed");
            payment.setTanggalPembayaran(new Date());
            selectedReg.setKonfirmasi(true); 
            JOptionPane.showMessageDialog(mainFrame, "Registration is confirmed and payment status is set as 'Successful'.");
        }
    }



    private void showTestManagement() {
        JFrame testFrame = new JFrame("Test Management");
        testFrame.setSize(500, 300);
        testFrame.setLayout(new GridLayout(3, 1));
        
        JButton createTestButton = new JButton("Create New Test Schedule");
        createTestButton.addActionListener(e -> createTestSchedule());
        testFrame.add(createTestButton);
        
        JButton inputResultButton = new JButton("Input Test Result");
        inputResultButton.addActionListener(e -> inputTestResult());
        testFrame.add(inputResultButton);
        
        JButton viewTestsButton = new JButton("View All Tests");
        viewTestsButton.addActionListener(e -> viewAllTests());
        testFrame.add(viewTestsButton);
        
        testFrame.setLocationRelativeTo(mainFrame);
        testFrame.setVisible(true);
    }

    private void createTestSchedule() {
        String date = JOptionPane.showInputDialog(mainFrame, "Enter test date (yyyy-mm-dd):");
        if (date != null && !date.isEmpty()) {
            try {
                JadwalTest jadwalTest = new JadwalTest(date);
                InfoTest info = new InfoTest(jadwalTest);
                infoTestList.add(info);
                JOptionPane.showMessageDialog(mainFrame, "Test schedule created successfully!");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void inputTestResult() {
        if (infoTestList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No test schedules available!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] testDates = new String[infoTestList.size()];
        for (int i = 0; i < infoTestList.size(); i++) {
            testDates[i] = infoTestList.get(i).getJadwalTest().getTanggalTest();
        }
        
        String selectedDate = (String) JOptionPane.showInputDialog(
                mainFrame,
                "Select test date:",
                "Input Test Result",
                JOptionPane.QUESTION_MESSAGE,
                null,
                testDates,
                testDates[0]);
        
        if (selectedDate != null) {
            String result = JOptionPane.showInputDialog(mainFrame, "Enter test result:");
            if (result != null && !result.isEmpty()) {
                for (InfoTest info : infoTestList) {
                    if (info.getJadwalTest().getTanggalTest().equals(selectedDate)) {
                        info.inputHasilTesOlehAdmin(result, "admin");
                        JOptionPane.showMessageDialog(mainFrame, "Test result saved successfully!");
                        break;
                    }
                }
            }
        }
    }

    private void viewAllTests() {
        if (infoTestList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No test information available!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (InfoTest info : infoTestList) {
            sb.append("Date: ").append(info.getJadwalTest().getTanggalTest()).append("\n");
            sb.append("Result: ").append(info.getHasilTes().isEmpty() ? "Not available" : info.getHasilTes()).append("\n\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JFrame testInfoFrame = new JFrame("All Test Information");
        testInfoFrame.add(scrollPane);
        testInfoFrame.setSize(400, 300);
        testInfoFrame.setLocationRelativeTo(mainFrame);
        testInfoFrame.setVisible(true);
    }

    private void showPaymentHistory() {
        if (paymentHistory.getAllPayments().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No payment history available!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JFrame paymentFrame = new JFrame("Payment History");
        paymentFrame.setSize(700, 400);
        
        String[] columnNames = {"ID", "Reg ID", "Amount", "VA Number", "Method", "Status", "Date"};
        List<Payment> payments = paymentHistory.getAllPayments();
        Object[][] data = new Object[payments.size()][7];
        
        for (int i = 0; i < payments.size(); i++) {
            Payment p = payments.get(i);
            data[i][0] = p.getIdPayment();
            data[i][1] = p.getIdRegistrasi();
            data[i][2] = p.getTotalHarga();
            data[i][3] = p.getVaNum();
            data[i][4] = p.getPaymentMethod();
            data[i][5] = p.getStatus();
            data[i][6] = p.getTanggalPembayaran();
        }
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        paymentFrame.add(scrollPane);
        paymentFrame.setLocationRelativeTo(mainFrame);
        paymentFrame.setVisible(true);
    }

    private void createSchedule() {
        JFrame scheduleFrame = new JFrame("Create Schedule");
        scheduleFrame.setSize(400, 300);
        scheduleFrame.setLayout(new GridLayout(6, 2, 5, 5));
        
        scheduleFrame.add(new JLabel("Subject:"));
        JTextField subjectField = new JTextField();
        scheduleFrame.add(subjectField);
        
        scheduleFrame.add(new JLabel("Date (yyyy-mm-dd):"));
        JTextField dateField = new JTextField();
        scheduleFrame.add(dateField);
        
        scheduleFrame.add(new JLabel("Start Time:"));
        JTextField startField = new JTextField();
        scheduleFrame.add(startField);
        
        scheduleFrame.add(new JLabel("End Time:"));
        JTextField endField = new JTextField();
        scheduleFrame.add(endField);
        
        scheduleFrame.add(new JLabel("Room:"));
        JTextField roomField = new JTextField();
        scheduleFrame.add(roomField);
        
        JButton createButton = new JButton("Create Schedule");
        createButton.addActionListener(e -> {
            try {
                Schedule schedule = new Schedule(
                    subjectField.getText(),
                    java.sql.Date.valueOf(dateField.getText()),
                    startField.getText(),
                    endField.getText(),
                    roomField.getText()
                );
                scheduleList.add(schedule);
                
                JOptionPane.showMessageDialog(scheduleFrame, "Schedule created successfully!");
                scheduleFrame.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(scheduleFrame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        scheduleFrame.add(createButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> scheduleFrame.dispose());
        scheduleFrame.add(cancelButton);
        
        scheduleFrame.setLocationRelativeTo(mainFrame);
        scheduleFrame.setVisible(true);
    }
    
    private void showStudentMenu() {
    	mainFrame.getContentPane().removeAll();
        
        JPanel studentPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Student Menu - Welcome " + currentUser.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        studentPanel.add(titleLabel, BorderLayout.NORTH);
        
	    JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
	
	    JButton registerButton = new JButton("Registration");
	    registerButton.addActionListener(e -> studentRegistration());
	    buttonPanel.add(registerButton);
	
	    JButton testButton = new JButton("Information Center");
	    testButton.addActionListener(e -> informationCenter());
	    buttonPanel.add(testButton);
	
	    JButton paymentButton = new JButton("Make Payment");
	    paymentButton.addActionListener(e -> makePayment());
	    buttonPanel.add(paymentButton);
	    
	    JButton checkPaymentButton = new JButton("Notification Center");
	    checkPaymentButton.addActionListener(e -> notificationCenter());
	    buttonPanel.add(checkPaymentButton);
	
	    JButton logoutButton = new JButton("Logout");
	    logoutButton.addActionListener(e -> { currentUser = null; showLoginPanel(); });
	    buttonPanel.add(logoutButton);
	    
	    JButton keluarButton = new JButton("Logout");
        keluarButton.addActionListener(e -> {
            currentUser = null;
            showLoginPanel();
        });
        buttonPanel.add(logoutButton);
        
        studentPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(studentPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    
    }
    
    private void studentRegistration() {
	        JFrame regFrame = new JFrame("Student Registration");
	        regFrame.setSize(500, 600);
	        regFrame.setLayout(new GridLayout(11, 2, 5, 5));
	        
	        regFrame.add(new JLabel("Nama Lengkap:"));
	        JTextField nameField = new JTextField();
	        regFrame.add(nameField);
	        
	        regFrame.add(new JLabel("Tanggal Lahir (yyyy-mm-dd):"));
	        JTextField birthField = new JTextField();
	        regFrame.add(birthField);
	        
	        regFrame.add(new JLabel("Jenis Kelamin:"));
	        JTextField genderField = new JTextField();
	        regFrame.add(genderField);
	        
	        regFrame.add(new JLabel("Alamat:"));
	        JTextField addressField = new JTextField();
	        regFrame.add(addressField);
	        
	        regFrame.add(new JLabel("No.Hp:"));
	        JTextField phoneField = new JTextField();
	        regFrame.add(phoneField);
	        
	        regFrame.add(new JLabel("Email:"));
	        JTextField emailField = new JTextField();
	        regFrame.add(emailField);
	        
	        regFrame.add(new JLabel("Nama Wali:"));
	        JTextField guardianField = new JTextField();
	        regFrame.add(guardianField);
	        
	        regFrame.add(new JLabel("No.Hp Wali:"));
	        JTextField guardianPhoneField = new JTextField();
	        regFrame.add(guardianPhoneField);
	        
	        regFrame.add(new JLabel("Metode Pembayaran:"));
	        JTextField paymentField = new JTextField();
	        regFrame.add(paymentField);
	        
	        JButton submitButton = new JButton("Submit");
	        submitButton.addActionListener(e -> {
	            try {
	                Registrasi reg = new Registrasi(
	                    nameField.getText(),
	                    java.sql.Date.valueOf(birthField.getText()),
	                    genderField.getText(),
	                    addressField.getText(),
	                    phoneField.getText(),
	                    emailField.getText(),
	                    guardianField.getText(),
	                    guardianPhoneField.getText(),
	                    paymentField.getText()
	                );
	                registrasiList.add(reg);
	                JOptionPane.showMessageDialog(regFrame, "Registration successful!\nYour ID: " + reg.getIdRegistrasi());
	                regFrame.dispose();
	            } catch (IllegalArgumentException ex) {
	                JOptionPane.showMessageDialog(regFrame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	        regFrame.add(submitButton);
	        
	        JButton cancelButton = new JButton("Cancel");
	        cancelButton.addActionListener(e -> regFrame.dispose());
	        regFrame.add(cancelButton);
	        
	        regFrame.setLocationRelativeTo(mainFrame);
	        regFrame.setVisible(true);
	    }
	
    private void informationCenter() {
        JFrame infoFrame = new JFrame("Information Center");
        infoFrame.setSize(500, 300);
        infoFrame.setLayout(new GridLayout(3, 1));
        
        JButton viewsButton = new JButton("View Schedule");
        viewsButton.addActionListener(e -> viewSchedule());
        infoFrame.add(viewsButton);
        
        JButton testButton = new JButton("Test Information");
        testButton.addActionListener(e -> viewAllTests());
        infoFrame.add(testButton);
    
        JButton rinfoButton = new JButton("Registration History");
        rinfoButton.addActionListener(e -> registrationHistory());
        infoFrame.add(rinfoButton);
        
        infoFrame.setLocationRelativeTo(mainFrame);
        infoFrame.setVisible(true);
        
    }
    
    private void viewSchedule() {
    	if (scheduleList == null || scheduleList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Belum ada jadwal tersedia.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame scheduleFrame = new JFrame("Schedule Bimbingan");
        scheduleFrame.setSize(700, 300);
        scheduleFrame.setLocationRelativeTo(mainFrame);

        String[] columnNames = {"Subject", "Date", "Start Time", "End Time", "Room"};
        String[][] data = new String[scheduleList.size()][5];
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule s = scheduleList.get(i);
            data[i][0] = s.getSubject();
            data[i][1] = sdf.format(s.getDate());
            data[i][2] = s.getStartTime();
            data[i][3] = s.getEndTime();
            data[i][4] = s.getRoom();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scheduleFrame.add(scrollPane);
        scheduleFrame.setVisible(true);
    }
    
    private void registrationHistory() {
        if (registrasiList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Belum ada data registrasi.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame historyFrame = new JFrame("Registration History");
        historyFrame.setSize(600, 500);
        historyFrame.setLayout(new BorderLayout());

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        for (Registrasi r : registrasiList) {
            sb.append(r.toString()).append("\n\n");
        }

        historyArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> historyFrame.dispose());
        historyFrame.add(closeButton, BorderLayout.SOUTH);

        historyFrame.setLocationRelativeTo(mainFrame);
        historyFrame.setVisible(true);
    }
    
	private void makePayment() {
        if (registrasiList.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Tidak ada data registrasi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Registrasi lastReg = registrasiList.get(registrasiList.size() - 1);

        boolean alreadyPaid = paymentHistory.getAllPayments().stream()
                .anyMatch(p -> p.getIdRegistrasi().equals(lastReg.getIdRegistrasi()));

        if (alreadyPaid && !(currentUser instanceof Admin)) {
            JOptionPane.showMessageDialog(mainFrame, "Pembayaran untuk registrasi ini sudah dilakukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean isAdmin = currentUser instanceof Admin;

        JFrame paymentFrame = new JFrame("Make Payment");
        paymentFrame.setSize(400, 300);
        paymentFrame.setLayout(new GridLayout(5, 2, 5, 5));

        paymentFrame.add(new JLabel("Registration ID:"));
        JTextField regIdField = new JTextField(lastReg.getIdRegistrasi());
        regIdField.setEditable(false);
        paymentFrame.add(regIdField);

        paymentFrame.add(new JLabel("Total Amount:"));
        JTextField amountField = new JTextField();
        paymentFrame.add(amountField);

        paymentFrame.add(new JLabel("VA Number:"));
        JTextField vaField = new JTextField();
        paymentFrame.add(vaField);

        paymentFrame.add(new JLabel("Payment Status:"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Lunas", "Belum Dikonfirmasi"});
        statusCombo.setSelectedItem("Belum Dikonfirmasi");
        statusCombo.setEnabled(isAdmin); 
        paymentFrame.add(statusCombo);

        JButton submitButton = new JButton("Submit Payment");
        submitButton.addActionListener(e -> {
            try {
                Payment pay = new Payment(
                    "PAY-" + (paymentHistory.getAllPayments().size() + 1),
                    lastReg.getIdRegistrasi(),
                    Integer.parseInt(amountField.getText()),
                    Integer.parseInt(vaField.getText()),
                    lastReg.getMetodePembayaran(),
                    (String) statusCombo.getSelectedItem(),
                    new Date()
                );
                paymentHistory.addPayment(pay);
                JOptionPane.showMessageDialog(paymentFrame, "Pembayaran berhasil!");
                paymentFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(paymentFrame, "Amount / VA tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        paymentFrame.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> paymentFrame.dispose());
        paymentFrame.add(cancelButton);

        paymentFrame.setLocationRelativeTo(mainFrame);
        paymentFrame.setVisible(true);
    }
	
	private void notificationCenter() {
	    JFrame notifFrame = new JFrame("Notification Center");
	    notifFrame.setSize(600, 400);
	    notifFrame.setLayout(new BorderLayout());

	    JTextArea notifArea = new JTextArea();
	    notifArea.setEditable(false);
	    notifArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    StringBuilder sb = new StringBuilder();

	    sb.append("=== Jadwal Bimbingan ===\n");
	    if (scheduleList == null || scheduleList.isEmpty()) {
	        sb.append("Belum ada jadwal tersedia.\n");
	    } else {
	        for (Schedule s : scheduleList) {
	            sb.append(s.tampilSchedule()).append("\n");
	        }
	    }
	    sb.append("\n=== Notifikasi Pembayaran ===\n");
	    if (registrasiList.isEmpty()) {
	        sb.append("Belum ada data registrasi.\n");
	    } else {
	        Registrasi lastReg = registrasiList.get(registrasiList.size() - 1);
	        Payment pay = paymentHistory.getAllPayments().stream()
	            .filter(p -> p.getIdRegistrasi().equals(lastReg.getIdRegistrasi()))
	            .findFirst().orElse(null);

	        if (pay == null) {
	            sb.append("Belum ada pembayaran yang dilakukan.\n");
	        } else {
	            sb.append("ID Pembayaran : ").append(pay.getIdPayment()).append("\n");
	            sb.append("Status        : ").append(pay.getStatus()).append("\n");
	            sb.append("Tanggal Bayar : ").append(new SimpleDateFormat("dd-MM-yyyy").format(pay.getTanggalPembayaran())).append("\n");
	            sb.append("Metode        : ").append(pay.getPaymentMethod()).append("\n");
	            sb.append("---------------------------------\n");
	        }
	    }

	    notifArea.setText(sb.toString());

	    JScrollPane scrollPane = new JScrollPane(notifArea);
	    notifFrame.add(scrollPane, BorderLayout.CENTER);

	    JButton closeButton = new JButton("Tutup");
	    closeButton.addActionListener(e -> notifFrame.dispose());
	    notifFrame.add(closeButton, BorderLayout.SOUTH);

	    notifFrame.setLocationRelativeTo(mainFrame);
	    notifFrame.setVisible(true);
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BimbleSwingApp());
    }
}
