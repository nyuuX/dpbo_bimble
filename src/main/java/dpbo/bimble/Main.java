package dpbo.bimble;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static Auth auth = new Auth();
    private static List<Registrasi> registrations = new ArrayList<>(); 
    private static List<Schedule> schedules = new ArrayList<>();  
    private static List<Notification> notifications = new ArrayList<>(); 
    private static PaymentHistory paymentHistory = new PaymentHistory(); 
    private static List<InfoTest> infoTests = new ArrayList<>();    

    private static int nextRegistrationId = 1;
    private static int nextPaymentId = 1;
    private static int nextNotificationId = 1;

    public static void main(String[] args) {
        System.out.println("Selamat Datang di Sistem Briton Bimbel Sederhana");

        while (true) {
            System.out.println("\n--- Menu Utama ---");
            System.out.println("1. Login");
            System.out.println("2. Keluar");
            int choice = MissionUtil.getInt("Pilih opsi");

            switch (choice) {
                case 1:
                    handleLogin(); 
                    break;
                case 2:
                    System.out.println("Terima kasih, sampai jumpa!");
                    MissionUtil.closeScanner(); 
                    return; 
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void handleLogin() {
        System.out.println("\n--- Login ---");
        String username = MissionUtil.getString("Masukkan Username");
        String password = MissionUtil.getPassword("Masukkan Password");

        User loggedInUser = auth.loginUser(username, password);

        if (loggedInUser != null) {
            loggedInUser.login(); 
            if (loggedInUser instanceof Admin) {
                showAdminMenu((Admin) loggedInUser);
            } else if (loggedInUser instanceof Student) {
                showStudentMenu((Student) loggedInUser);
            }
        }
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== ADMIN MENU (" + admin.getUsername() + ") ===");
            System.out.println("1. Tambah Jadwal Bimbingan");
            System.out.println("2. Lihat Semua Pendaftar");
            System.out.println("3. Konfirmasi Pendaftaran Siswa");
            System.out.println("4. Konfirmasi Pembayaran Siswa");
            System.out.println("5. Input Hasil Tes Siswa");
            System.out.println("6. Lihat Riwayat Pembayaran");
            System.out.println("7. Logout");

            int pil = MissionUtil.getInt("Pilih menu: ");
            switch (pil) {
                case 1:
                    addSchedule(admin);
                    break;
                case 2:
                    displayRegistrations();
                    break;
                case 3:
                    confirmStudentRegistration(admin);
                    break;
                case 4:
                    confirmStudentPayment(admin);
                    break;
                case 5:
                    enterStudentTestResult(admin);
                    break;
                case 6:
                    paymentHistory.printAllHistory();
                    break;
                case 7:
                    admin.logout(); 
                    return; 
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void showStudentMenu(Student student) {
        while (true) {
            System.out.println("\n=== STUDENT MENU (" + student.getUsername() + ") ===");
            System.out.println("1. Daftar Kursus");
            System.out.println("2. Lakukan Pembayaran");
            System.out.println("3. Lihat Notifikasi");
            System.out.println("4. Lihat Jadwal Bimbingan");
            System.out.println("5. Lihat Jadwal & Hasil Tes");
            System.out.println("6. Logout");

            int pilihan = MissionUtil.getInt("Pilih menu: ");
            switch (pilihan) {
                case 1:
                    boolean alreadyRegistered = false;
                    for (Registrasi r : registrations) {
                        if (r.getStudent() != null && r.getStudent().equals(student)) {
                            alreadyRegistered = true;
                            break;
                        }
                    }
                    if (!alreadyRegistered) {
                        registerForCourse(student);
                    } else {
                        System.out.println("Anda sudah terdaftar untuk kursus.");
                    }
                    break;
                case 2:
                    makePayment(student);
                    break;
                case 3:
                    viewNotifications(student);
                    break;
                case 4:
                    displaySchedules();
                    break;
                case 5:
                    viewTestInfo(student);
                    break;
                case 6:
                    student.logout();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void addSchedule(Admin admin) {
        System.out.println("\n--- Tambah Jadwal Bimbingan Baru ---");
        String subject = MissionUtil.getString("Mata Pelajaran");
        Date date = parseDate(MissionUtil.getString("Tanggal (yyyy-MM-dd)"));
        String startTime = MissionUtil.getString("Jam Mulai (HH:mm)");
        String endTime = MissionUtil.getString("Jam Selesai (HH:mm)");
        String room = MissionUtil.getString("Ruangan");

        Schedule newSchedule = new Schedule(subject, date, startTime, endTime, room);
        schedules.add(newSchedule); 
        admin.addSchedule(newSchedule); 
        System.out.println("Jadwal berhasil ditambahkan.");

        for (Registrasi reg : registrations) {
            if (reg.isKonfirmasi()) {
                notifications.add(new ScheduleNotification(
                    "NOTIF-" + nextNotificationId++,
                    "Jadwal baru untuk " + subject + " pada " + new SimpleDateFormat("dd-MM-yyyy").format(date) + " telah ditambahkan.",
                    "Jadwal Baru", reg, null, newSchedule, new Date(), subject
                ));
            }
        }
        System.out.println("Notifikasi jadwal telah dikirim ke siswa yang terkonfirmasi.");
    }

    private static void displayRegistrations() {
        System.out.println("\n--- Daftar Semua Pendaftar ---");
        if (registrations.isEmpty()) {
            System.out.println("Belum ada pendaftar.");
        } else {
            registrations.forEach(System.out::println); 
        }
    }

    private static void confirmStudentRegistration(Admin admin) {
        System.out.println("\n--- Konfirmasi Pendaftaran ---");
        List<Registrasi> pending = new ArrayList<>();
        for (Registrasi r : registrations) {
            if (!r.isKonfirmasi()) {
                pending.add(r);
            }
        }

        if (pending.isEmpty()) {
            System.out.println("Tidak ada pendaftaran yang menunggu konfirmasi.");
            return;
        }

        System.out.println("Pendaftar yang Belum Dikonfirmasi:");
        for (Registrasi r : pending) {
            System.out.println("ID: " + r.getIdRegistrasi() + ", Nama: " + r.getNamaLengkap());
        }

        String regId = MissionUtil.getString("Masukkan ID Registrasi yang akan dikonfirmasi");
        Registrasi regToConfirm = null;
        for (Registrasi r : registrations) {
            if (r.getIdRegistrasi().equalsIgnoreCase(regId)) {
                regToConfirm = r;
                break;
            }
        }

        if (regToConfirm == null) {
            System.out.println("ID Registrasi tidak ditemukan.");
            return;
        }
        if (regToConfirm.isKonfirmasi()) {
            System.out.println("Pendaftaran ini sudah dikonfirmasi sebelumnya.");
            return;
        }

        Payment payment = null;
        for (Payment p : paymentHistory.getAllPayments()) {
            if (p.getIdRegistrasi().equals(regToConfirm.getIdRegistrasi())) {
                payment = p;
                break;
            }
        }

        if (payment != null && (payment.getStatus().equalsIgnoreCase("Completed") || payment.getStatus().equalsIgnoreCase("Sukses"))) {
            regToConfirm.setKonfirmasi(true); 
            admin.confirmRegistrations();
            System.out.println("Pendaftaran " + regId + " berhasil dikonfirmasi.");

            boolean infoTestExists = false;
            for (InfoTest it : infoTests) {
                if (it.getAssociatedRegistrationId() != null && it.getAssociatedRegistrationId().equals(regId)) {
                    infoTestExists = true;
                    break;
                }
            }
            if (!infoTestExists) {

                InfoTest newInfoTest = new InfoTest(new JadwalTest("Belum ditentukan"), regId);
                newInfoTest.setAssociatedRegistrationId(regId); // Set the associated ID
                infoTests.add(newInfoTest);
                System.out.println("Informasi tes awal untuk siswa ini telah dibuat.");
            }

            notifications.add(new PaymentNotification(
                "NOTIF-" + nextNotificationId++,
                "Pendaftaran Anda dengan ID " + regId + " telah Dikonfirmasi. Selamat datang di Briton Bimbel!",
                "Konfirmasi Pendaftaran", regToConfirm, "Dikonfirmasi", null, new Date(), "Pendaftaran"
            ));
        } else {
            System.out.println("Gagal konfirmasi: Pembayaran belum lunas atau tidak ditemukan untuk registrasi ini.");
        }
    }

    private static void confirmStudentPayment(Admin admin) {
        System.out.println("\n--- Konfirmasi Pembayaran ---");
        List<Payment> pendingPayments = new ArrayList<>();
        for (Payment p : paymentHistory.getAllPayments()) {
            if (p.getStatus().equalsIgnoreCase("Completed")) {
                pendingPayments.add(p);
            }
        }

        if (pendingPayments.isEmpty()) {
            System.out.println("Tidak ada pembayaran yang menunggu konfirmasi admin.");
            return;
        }

        System.out.println("Pembayaran yang Menunggu Konfirmasi:");
        for (Payment p : pendingPayments) {
            p.printPaymentInfo();
        }

        String paymentId = MissionUtil.getString("Masukkan ID Pembayaran yang akan dikonfirmasi");
        Payment paymentToConfirm = null;
        for (Payment p : paymentHistory.getAllPayments()) {
            if (p.getIdPayment().equalsIgnoreCase(paymentId)) {
                paymentToConfirm = p;
                break;
            }
        }

        if (paymentToConfirm == null) {
            System.out.println("ID Pembayaran tidak ditemukan.");
            return;
        }
        if (paymentToConfirm.getStatus().equalsIgnoreCase("Sukses")) {
            System.out.println("Pembayaran ini sudah dikonfirmasi lunas.");
            return;
        }
        if (!paymentToConfirm.getStatus().equalsIgnoreCase("Completed")) {
            System.out.println("Pembayaran ini belum dilakukan oleh siswa atau statusnya tidak 'Completed'.");
            return;
        }

        admin.confirmPayment(paymentToConfirm); 
        System.out.println("Pembayaran " + paymentId + " berhasil dikonfirmasi sebagai lunas oleh Admin.");

        Registrasi relatedReg = null;
        for (Registrasi r : registrations) {
            if (r.getIdRegistrasi().equals(paymentToConfirm.getIdRegistrasi())) {
                relatedReg = r;
                break;
            }
        }
        
        if (relatedReg != null) {
            notifications.add(new PaymentNotification(
                "NOTIF-" + nextNotificationId++,
                "Pembayaran Anda untuk pendaftaran " + relatedReg.getIdRegistrasi() + " telah Lunas dikonfirmasi oleh Admin.",
                "Konfirmasi Pembayaran", relatedReg, "Sukses", null, new Date(), "Pembayaran"
            ));
        }
    }

    private static void enterStudentTestResult(Admin admin) {
        System.out.println("\n--- Input Hasil Tes Siswa ---");
        String regId = MissionUtil.getString("Masukkan ID Registrasi siswa untuk menginput hasil tes");
        
        InfoTest studentTestInfo = null;
        for (InfoTest it : infoTests) {
            if (it.getAssociatedRegistrationId() != null && it.getAssociatedRegistrationId().equals(regId)) {
                studentTestInfo = it;
                break;
            }
        }

        if (studentTestInfo == null) {
            System.out.println("Informasi tes tidak ditemukan untuk ID Registrasi ini. Pastikan siswa sudah dikonfirmasi.");
            return;
        }

        String testResult = MissionUtil.getString("Masukkan hasil tes (Contoh: Lulus, Tidak Lulus, Baik, Cukup)");
        studentTestInfo.inputHasilTesOlehAdmin(testResult, admin.getRole()); 
        Registrasi relatedReg = null;
        for (Registrasi r : registrations) {
            if (r.getIdRegistrasi().equals(regId)) {
                relatedReg = r;
                break;
            }
        }

        if (relatedReg != null) {
            notifications.add(new PaymentNotification( 
                "NOTIF-" + nextNotificationId++,
                "Hasil tes Anda telah diinput: " + testResult + ". Cek menu 'Jadwal & Hasil Tes' Anda.",
                "Hasil Tes", relatedReg, testResult, null, new Date(), "Hasil Tes"
            ));
        }
    }

    private static void registerForCourse(Student student) {
        System.out.println("\n--- Formulir Pendaftaran Kursus ---");
        String namaLengkap = MissionUtil.getString("Nama Lengkap");
        Date tglLahir = parseDate(MissionUtil.getString("Tanggal Lahir (yyyy-MM-dd)"));
        String jenisKelamin = MissionUtil.getString("Jenis Kelamin (Pria/Wanita)");
        String alamat = MissionUtil.getString("Alamat");
        String noHp = MissionUtil.getString("No. HP");
        String namaWali = MissionUtil.getString("Nama Wali (jika ada, kosongkan jika tidak)");
        String noHpWali = MissionUtil.getString("No. HP Wali (jika ada, kosongkan jika tidak)");
        String metodePembayaran = MissionUtil.getString("Metode Pembayaran (Transfer/VA)");

        String newRegId = "REG-" + String.format("%04d", nextRegistrationId++);
        Registrasi newRegistration = new Registrasi(newRegId, tglLahir, jenisKelamin,
                                        alamat, noHp, student.getUsername(), namaWali, noHpWali, metodePembayaran);
        registrations.add(newRegistration);

        int courseFee = 500000; 
        int virtualAccountNum = (int)(Math.random() * 900000) + 100000; 
        String paymentId = "PAY-" + String.format("%04d", nextPaymentId++);

        Payment newPayment = new Payment(paymentId, newRegId, courseFee, virtualAccountNum, metodePembayaran, "Pending", null);
        paymentHistory.addPayment(newPayment); 

        System.out.println("Pendaftaran berhasil diajukan. ID Registrasi Anda: " + newRegId);
        System.out.println("Silakan lakukan pembayaran.");

        notifications.add(new PaymentNotification(
            "NOTIF-" + nextNotificationId++,
            "Pendaftaran Anda berhasil. Lakukan pembayaran sebesar Rp" + courseFee + " ke VA " + virtualAccountNum + " (ID Pembayaran: " + paymentId + ").",
            "Instruksi Pembayaran", newRegistration, "Pending", null, new Date(), "Pembayaran"
        ));
    }

    private static void makePayment(Student student) {
        System.out.println("\n--- Lakukan Pembayaran ---");
        String regId = MissionUtil.getString("Masukkan ID Registrasi Anda untuk melakukan pembayaran");

        Registrasi studentRegistration = null;
        for (Registrasi r : registrations) {
            if (r.getIdRegistrasi().equalsIgnoreCase(regId) && r.getStudent() != null && r.getStudent().equals(student)) {
                studentRegistration = r;
                break;
            }
        }

        if (studentRegistration == null) {
            System.out.println("ID Registrasi tidak ditemukan atau bukan milik Anda. Silakan coba lagi.");
            return;
        }

        Payment paymentToProcess = null;
        for (Payment p : paymentHistory.getAllPayments()) {
            if (p.getIdRegistrasi().equals(studentRegistration.getIdRegistrasi())) {
                paymentToProcess = p;
                break;
            }
        }

        if (paymentToProcess == null) {
            System.out.println("Tidak ada data pembayaran untuk pendaftaran Anda. Mohon hubungi admin.");
            return;
        }

        if (paymentToProcess.getStatus().equalsIgnoreCase("Sukses")) {
            System.out.println("Pembayaran Anda sudah lunas. Terima kasih!");
            return;
        }
        if (paymentToProcess.getStatus().equalsIgnoreCase("Completed")) {
            System.out.println("Anda sudah melakukan pembayaran. Menunggu konfirmasi dari Admin.");
            return;
        }

        System.out.println("Detail Pembayaran Anda:");
        paymentToProcess.printPaymentInfo(); 

        String confirmPay = MissionUtil.getString("Ketik 'BAYAR' untuk mengkonfirmasi pembayaran ini");
        if (confirmPay.equalsIgnoreCase("BAYAR")) {
            paymentToProcess.setStatus("Completed"); 
            paymentToProcess.setTanggalPembayaran(new Date());
            System.out.println("Pembayaran berhasil Anda lakukan. Menunggu konfirmasi Admin.");

            notifications.add(new PaymentNotification(
                "NOTIF-" + nextNotificationId++,
                "Anda telah melakukan pembayaran untuk ID Registrasi " + studentRegistration.getIdRegistrasi() + ". Menunggu konfirmasi Admin.",
                "Pembayaran Dilakukan", studentRegistration, "Completed", null, new Date(), "Pembayaran"
            ));
        } else {
            System.out.println("Pembayaran dibatalkan.");
        }
    }

    private static void displaySchedules() {
        System.out.println("\n--- Jadwal Bimbingan Tersedia ---");
        if (schedules.isEmpty()) {
            System.out.println("Belum ada jadwal bimbingan yang diatur.");
        } else {
            schedules.forEach(Schedule::printSchedule);
        }
    }

    private static void viewNotifications(Student student) {
        System.out.println("\n--- Notifikasi Anda ---");
        boolean foundNotifications = false;
        for (Notification n : notifications) {
            if (n.getRegistration() != null && n.getRegistration().getStudent() != null && n.getRegistration().getStudent().equals(student)) {
                System.out.println("---------------------------------");
                System.out.println(n.getNotificationDetails()); 
                foundNotifications = true;
            }
        }
        if (!foundNotifications) {
            System.out.println("Tidak ada notifikasi baru untuk Anda.");
        }
        System.out.println("---------------------------------");
    }

    private static void viewTestInfo(Student student) {
        System.out.println("\n--- Jadwal dan Hasil Tes Anda ---");
        String regId = MissionUtil.getString("Masukkan ID Registrasi Anda untuk melihat info tes");

        Registrasi studentReg = null;
        for (Registrasi r : registrations) {
            if (r.getIdRegistrasi().equalsIgnoreCase(regId) && r.getStudent() != null && r.getStudent().equals(student)) {
                studentReg = r;
                break;
            }
        }

        if (studentReg == null) {
            System.out.println("ID Registrasi tidak ditemukan atau bukan milik Anda.");
            return;
        }

        InfoTest myInfoTest = null;
        for (InfoTest it : infoTests) {
            if (it.getAssociatedRegistrationId() != null && it.getAssociatedRegistrationId().equals(regId)) {
                myInfoTest = it;
                break;
            }
        }

        if (myInfoTest == null) {
            System.out.println("Informasi tes belum tersedia untuk ID Registrasi ini. Mungkin pendaftaran Anda belum dikonfirmasi admin atau hasil tes belum diinput.");
        } else {
            myInfoTest.tampilkanInfoTest();
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Format tanggal salah (gunakan YYYY-MM-DD). Menggunakan tanggal hari ini.");
            return new Date();
        }
    }
}
