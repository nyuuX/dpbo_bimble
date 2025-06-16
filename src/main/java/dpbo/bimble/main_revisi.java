package dpbo.bimble;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Auth auth = new Auth();

        List<Registrasi> registrasiList = new ArrayList<>();
        List<InfoTest> infoTestList = new ArrayList<>();
        List<Schedule> scheduleList = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();
        PaymentHistory paymentHistory = new PaymentHistory();

        Admin admin = new Admin("admin1", "adminpass", "Admin Utama", "admin");
        auth.registerUser(admin);

        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu Login =====");
            System.out.print("Login sebagai (admin/student/exit): ");
            String role = sc.nextLine().toLowerCase();

            if (role.equals("exit")) {
                running = false;
                break;
            }

            System.out.print("Masukkan username: ");
            String uname = sc.nextLine();

            System.out.print("Masukkan password: ");
            String pass = sc.nextLine();

            if (role.equals("student")) {
                System.out.print("Masukkan nama: ");
                String name = sc.nextLine();
                Student student = new Student(uname, pass, name);
                auth.registerUser(student);
            }

            User user = auth.loginUser(uname, pass);

            if (user == null) {
                System.out.println("Login gagal.");
                continue;
            }

            System.out.println("\nLogin berhasil sebagai " + user.getClass().getSimpleName());

            if (user instanceof Student) {
                boolean studentMenu = true;
                while (studentMenu) {
                    System.out.println("\n===== Menu Student =====");
                    System.out.println("1. Isi Data Registrasi");
                    System.out.println("2. Lihat Info Tes (Jadwal Pelajaran)");
                    System.out.println("3. Lihat Hasil Tes");
                    System.out.println("4. Lakukan Pembayaran");
                    System.out.println("5. Lihat Status Pembayaran");
                    System.out.println("6. Logout");
                    System.out.print("Pilih: ");
                    int choice = sc.nextInt(); sc.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.println("=== Input Data Registrasi ===");
                            System.out.print("Nama Lengkap: ");
                            String nama = sc.nextLine();
                            System.out.print("Tanggal Lahir (yyyy-mm-dd): ");
                            String tgl = sc.nextLine();
                            System.out.print("Jenis Kelamin: ");
                            String jk = sc.nextLine();
                            System.out.print("Alamat: ");
                            String alamat = sc.nextLine();
                            System.out.print("No HP: ");
                            String noHp = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Nama Wali: ");
                            String wali = sc.nextLine();
                            System.out.print("No HP Wali: ");
                            String hpWali = sc.nextLine();
                            System.out.print("Metode Pembayaran: ");
                            String metode = sc.nextLine();

                            Date tanggalLahir;
                            try {
                                tanggalLahir = java.sql.Date.valueOf(tgl);
                            } catch (Exception e) {
                                System.out.println("Format tanggal salah.");
                                break;
                            }

                            Registrasi reg = new Registrasi(nama, tanggalLahir, jk, alamat, noHp, email, wali, hpWali, metode);
                            registrasiList.add(reg);
                            System.out.println(reg);
                            break;

                        case 2:
                            System.out.println("\n=== Jadwal Pelajaran Tersedia ===");
                            if (scheduleList.isEmpty()) {
                                System.out.println("Belum ada jadwal pelajaran yang tersedia.");
                            } else {
                                for (Schedule s : scheduleList) {
                                    System.out.println("=========== Schedule ===========");
                                    System.out.println("Subject : " + s.getSubject());
                                    System.out.println("Date    : " + s.getDate());
                                    System.out.println("Time    : " + s.getStartTime() + " - " + s.getEndTime());
                                    System.out.println("Room    : " + s.getRoom());
                                    System.out.println("================================");
                                }
                            }
                            break;

                        case 3:
                            System.out.println("\n=== Hasil Tes ===");
                            boolean adaNilai = false;
                            for (Schedule s : scheduleList) {
                                if (s.getHasilTes() != null) {
                                    s.printSchedule();
                                    System.out.println("Hasil Tes: " + s.getHasilTes());
                                    adaNilai = true;
                                }
                            }
                            if (!adaNilai) {
                                System.out.println("Belum ada hasil tes yang tersedia.");
                            }
                            break;

                        case 4:
                            if (registrasiList.isEmpty()) {
                                System.out.println("Belum ada registrasi.");
                                break;
                            }

                            Registrasi lastReg = registrasiList.get(registrasiList.size() - 1);

                            int total = 500000;
                            System.out.println("Total Harga: Rp" + total);

                            System.out.print("Masukkan jumlah yang dibayarkan: ");
                            int bayar = sc.nextInt(); sc.nextLine();

                            System.out.print("VA Number: ");
                            int va = sc.nextInt(); sc.nextLine();

                            String status = (bayar >= total) ? "Menunggu Konfirmasi Admin" : "Belum Lunas";

                            String idPembayaran = "PB-" + (paymentHistory.getAllPayments().size() + 1);
                            Payment pay = new Payment(
                                "PAY-" + (paymentHistory.getAllPayments().size() + 1),
                                lastReg.getIdRegistrasi(),
                                total,
                                va,
                                lastReg.getMetodePembayaran(),
                                status,
                                new Date(),
                                idPembayaran 
                            );
                            paymentHistory.addPayment(pay);
                            System.out.println("Pembayaran tercatat. Status saat ini: " + status);

                            PaymentNotification notif = new PaymentNotification(
                            	    "NTF-" + (notifications.size() + 1),                             
                            	    "Pembayaran ID: " + pay.getIdPembayaran() + " sedang diproses", 
                            	    "Payment",                                                       
                            	    lastReg,                                                         
                            	    status,                                                          
                            	    null,                                                            
                            	    pay.getTanggalPembayaran(),                                      
                            	    null,
                            	    null,
                            	    null
                            	);
                            	notifications.add(notif);
                            	System.out.println("Notifikasi: " + notif.getMessage());
                            break;

                        case 5:
                            System.out.println("\n=== Status Pembayaran Anda ===");
                            List<Payment> payments = paymentHistory.getAllPayments();
                            boolean ditemukan = false;
                            for (Payment p : payments) {
                                if (p.getIdRegistrasi().equals(registrasiList.get(registrasiList.size() - 1).getIdRegistrasi())) {
                                    System.out.println(p);
                                    ditemukan = true;
                                }
                            }
                            if (!ditemukan) {
                                System.out.println("Belum ada pembayaran yang tercatat.");
                            }
                            break;

                        case 6:
                            System.out.println("Logout...");
                            studentMenu = false;
                            break;
                    }
                }
            }

            if (user instanceof Admin) {
                boolean adminMenu = true;
                while (adminMenu) {
                    System.out.println("\n===== Menu Admin =====");
                    System.out.println("1. Lihat Semua Registrasi");
                    System.out.println("2. Konfirmasi Registrasi");
                    System.out.println("3. Lihat Riwayat Pembayaran");
                    System.out.println("4. Konfirmasi Pembayaran");
                    System.out.println("5. Buat Jadwal Pelajaran");
                    System.out.println("6. Input & Lihat Hasil Tes");
                    System.out.println("7. Logout");
                    System.out.print("Pilih: ");
                    int choice = sc.nextInt(); sc.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.println("\n=== Semua Registrasi ===");
                            if (registrasiList.isEmpty()) {
                                System.out.println("Belum ada data registrasi.");
                            } else {
                            	int i = 1;
                            	for (Registrasi r : registrasiList) {
                            	    System.out.println("=====[ Registrasi #" + i + " ]=====");
                            	    System.out.println(r);
                            	    System.out.println();
                            	    i++;
                            	}
                            }
                            break;

                        case 2:
                        	 System.out.println("\n=== Konfirmasi Registrasi ===");
                        	    List<Registrasi> belumDikonfirmasi = new ArrayList<>();
                        	    for (Registrasi r : registrasiList) {
                        	        if (!r.isKonfirmasi()) {
                        	            belumDikonfirmasi.add(r);
                        	        }
                        	    }

                        	    if (belumDikonfirmasi.isEmpty()) {
                        	        System.out.println("Semua registrasi sudah dikonfirmasi.");
                        	        break;
                        	    }

                        	    for (int i = 0; i < belumDikonfirmasi.size(); i++) {
                        	        System.out.println("[" + (i + 1) + "] " + belumDikonfirmasi.get(i));
                        	    }

                        	    System.out.print("Pilih nomor registrasi yang ingin dikonfirmasi (0 untuk batal): ");
                        	    int pilihan = sc.nextInt(); sc.nextLine();

                        	    if (pilihan > 0 && pilihan <= belumDikonfirmasi.size()) {
                        	        Registrasi selected = belumDikonfirmasi.get(pilihan - 1);
                        	        selected.setKonfirmasi(true);
                        	        System.out.println("Registrasi dengan ID " + selected.getIdRegistrasi() + " telah dikonfirmasi.");
                        	    } else if (pilihan == 0) {
                        	        System.out.println("Konfirmasi dibatalkan.");
                        	    } else {
                        	        System.out.println("Pilihan tidak valid.");
                        	    }
                        	    break;
                        case 3:
                            paymentHistory.printAllHistory();
                            break;

                        case 4:
                            System.out.println("\n=== Konfirmasi Pembayaran ===");
                            List<Payment> allPayments = paymentHistory.getAllPayments();
                            boolean adaYangBelumLunas = false;
                            for (Payment p : allPayments) {
                                if (!p.getStatus().equalsIgnoreCase("Lunas")) {
                                    System.out.println(p);
                                    System.out.print("Konfirmasi pembayaran ini? (y/n): ");
                                    String yn = sc.nextLine().toLowerCase();
                                    if (yn.equals("y")) {
                                        p.setStatus("Lunas");
                                        System.out.println("Status pembayaran berhasil diubah menjadi Lunas.");
                                    } else {
                                        System.out.println("Dilewati.");
                                    }
                                    adaYangBelumLunas = true;
                                }
                            }
                            if (!adaYangBelumLunas) {
                                System.out.println("Semua pembayaran sudah dikonfirmasi (Lunas).");
                            }
                            break;

                        case 5:
                            System.out.print("Mata Pelajaran: ");
                            String subject = sc.nextLine();
                            System.out.print("Tanggal (yyyy-mm-dd): ");
                            String tglMapel = sc.nextLine();
                            System.out.print("Waktu Mulai: ");
                            String start = sc.nextLine();
                            System.out.print("Waktu Selesai: ");
                            String end = sc.nextLine();
                            System.out.print("Ruangan: ");
                            String room = sc.nextLine();

                            try {
                                Schedule schedule = new Schedule(subject, java.sql.Date.valueOf(tglMapel), start, end, room);
                                scheduleList.add(schedule);
                                schedule.printSchedule();

                                ScheduleNotification notif = new ScheduleNotification(
                                    "NTF-" + (notifications.size() + 1),
                                    "Jadwal baru telah dibuat untuk mata pelajaran " + subject,
                                    "Schedule",
                                    null,
                                    null,
                                    schedule,
                                    java.sql.Date.valueOf(tglMapel),
                                    subject,
                                    room,
                                    start + " - " + end
                                );
                                notifications.add(notif);
                                System.out.println("=== NOTIFIKASI ===");
                                System.out.println(notif.getNotificationDetails());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Format tanggal salah.");
                            }
                            break;

                        case 6:
                            System.out.println("\n=== Input & Lihat Hasil Tes ===");
                            if (scheduleList.isEmpty()) {
                                System.out.println("Belum ada jadwal yang tersedia.");
                            } else {
                                for (Schedule s : scheduleList) {
                                    s.printSchedule();
                                    if (s.getHasilTes() == null) {
                                        System.out.print("Masukkan hasil tes (kosongkan jika tidak ingin mengisi): ");
                                        String hasil = sc.nextLine();
                                        if (!hasil.isEmpty()) {
                                            s.setHasilTes(hasil);
                                            System.out.println("Hasil tes disimpan.");
                                        } else {
                                            System.out.println("Dilewati.");
                                        }
                                    } else {
                                        System.out.println("Hasil Tes: " + s.getHasilTes());
                                        System.out.print("Ingin ubah hasil tes? (y/n): ");
                                        String yn = sc.nextLine().toLowerCase();
                                        if (yn.equals("y")) {
                                            System.out.print("Masukkan hasil tes baru: ");
                                            String hasilBaru = sc.nextLine();
                                            s.setHasilTes(hasilBaru);
                                            System.out.println("Hasil tes diperbarui.");
                                        } else {
                                            System.out.println("Dilewati.");
                                        }
                                    }
                                    System.out.println();
                                }
                            }
                            break;

                        case 7:
                            System.out.println("Logout...");
                            adminMenu = false;
                            break;
                    }
                }
            }
        }

        System.out.println("Program selesai.");
        sc.close();
    }
}
