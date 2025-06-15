package dpbo.bimble;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Auth auth = new Auth();

        List<Registrasi> registrasiList = new ArrayList<>();
        List<InfoTest> infoTestList = new ArrayList<>();
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
                    System.out.println("2. Lihat Info Tes");
                    System.out.println("3. Lakukan Pembayaran");
                    System.out.println("4. Logout");
                    System.out.print("Pilih: ");
                    int choice = sc.nextInt(); sc.nextLine();

                    switch (choice) {
                        case 1:
                        	Registrasi reg = Registrasi.registrasi(sc);
                            if (reg != null) {
                            	registrasiList.add(reg);
                                System.out.println("\nData registrasi berhasil disimpan:");
                                System.out.println(reg);
                            } else {
                                System.out.println("\nRegistrasi gagal. Silakan ulangi lagi.");
                            }
                            break;


                        case 2:
                        	if (infoTestList.isEmpty()) {
                                System.out.println("Belum ada info tes.");
                            } else {
                                System.out.println("\n=== Info Tes ===");
                                for (InfoTest info : infoTestList) {
                                    info.tampilkanInfoTest();
                                }
                            }
                            break;
                        case 3:
                            if (registrasiList.isEmpty()) {
                                System.out.println("Belum ada registrasi.");
                                break;
                            }

                            Registrasi lastReg = registrasiList.get(registrasiList.size() - 1);
                            System.out.print("Total Harga: ");
                            int total = sc.nextInt(); sc.nextLine();
                            System.out.print("VA Number: ");
                            int va = sc.nextInt(); sc.nextLine();
                            System.out.print("Status Pembayaran (Lunas/Belum): ");
                            String status = sc.nextLine();

                            Payment pay = new Payment("PAY-" + (paymentHistory.getAllPayments().size() + 1),
                                    lastReg.getIdRegistrasi(), total, va, lastReg.getMetodePembayaran(), status, new Date());
                            paymentHistory.addPayment(pay);
                            System.out.println("Pembayaran berhasil ditambahkan.");
                            break;

                        case 4:
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
                    System.out.println("2. Input & Lihat Hasil Tes");
                    System.out.println("3. Lihat Riwayat Pembayaran");
                    System.out.println("4. Buat Jadwal Pelajaran");
                    System.out.println("5. Logout");
                    System.out.print("Pilih: ");
                    int choice = sc.nextInt(); sc.nextLine();

                    switch (choice) {
                        case 1:
                        	if (registrasiList.isEmpty()) {
                                System.out.println("Belum ada data registrasi!!");
                            } else {
                                System.out.println("=== Daftar Data Registrasi ===");
                                for (Registrasi r : registrasiList) {
                                    System.out.println(r);
                                }
                            }
                            break;

                        case 2:
                            System.out.println("\n1. Buat Jadwal Tes Baru");
                            System.out.println("2. Input Hasil Tes");
                            System.out.print("Pilih opsi: ");
                            String pilihanTes = sc.nextLine();

                            if (pilihanTes.equals("1")) {
                                System.out.print("Tanggal Tes (yyyy-mm-dd): ");
                                String tglTes = sc.nextLine();

                                try {
                                    JadwalTest jadwalTest = new JadwalTest(tglTes);
                                    InfoTest info = new InfoTest(jadwalTest);
                                    infoTestList.add(info);
                                    System.out.println("Jadwal tes berhasil dibuat.");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Format tanggal salah.");
                                }

                            } else if (pilihanTes.equals("2")) {
                                if (infoTestList.isEmpty()) {
                                    System.out.println("Belum ada info tes yang tersedia.");
                                } else {
                                    System.out.println("\nPilih Jadwal untuk Input Hasil Tes:");
                                    for (int i = 0; i < infoTestList.size(); i++) {
                                        System.out.println("[" + i + "] " + infoTestList.get(i).getJadwalTest().getTanggalTest());
                                    }

                                    System.out.print("Masukkan indeks jadwal: ");
                                    String idxStr = sc.nextLine();

                                    try {
                                        int idx = Integer.parseInt(idxStr);
                                        if (idx >= 0 && idx < infoTestList.size()) {
                                            System.out.print("Masukkan hasil tes: ");
                                            String hasil = sc.nextLine();
                                            infoTestList.get(idx).inputHasilTesOlehAdmin(hasil, "admin");
                                            System.out.println("Hasil tes berhasil disimpan.");
                                        } else {
                                            System.out.println("Indeks tidak valid.");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Input harus berupa angka.");
                                    }
                                }
                            }
                            break;


                        case 3:
                            paymentHistory.printAllHistory();
                            break;

                        case 4:
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
                                schedule.printSchedule();
                            } catch (IllegalArgumentException e) {
                                System.out.println("Format tanggal salah.");
                            }
                            break;

                        case 5:
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
