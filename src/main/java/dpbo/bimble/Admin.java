package dpbo.bimble;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(String userId, String username, String password) {
        super(userId, username, password, "ADMIN");
    }

    @Override
    public void login() {
        System.out.println("Admin '" + getUsername() + "' melakukan tindakan spesifik setelah login.");
        System.out.println("Contoh: Menampilkan dashboard admin.");
    }

    @Override
    public void logout() {
        System.out.println("Admin '" + getUsername() + "' melakukan tindakan spesifik saat logout.");
    }

    @Override
    public void register() {
        System.out.println("Admin '" + getUsername() + "' melakukan proses 'Register'.");
        System.out.println("Catatan: Registrasi admin mungkin memiliki alur berbeda atau tidak diizinkan melalui metode ini.");
    }

    public void viewRegistrations() {
        System.out.println("Admin '" + getUsername() + "' sedang melihat data pendaftaran.");
    }

    public void confirmRegistrations() {
        System.out.println("Admin '" + getUsername() + "' sedang mengkonfirmasi pendaftaran.");
    }

    public void addSchedule(Schedule schedule) {
        System.out.println("Admin '" + getUsername() + "' menambahkan jadwal: " + schedule.toString());
    }

    public void updateSchedule(Schedule schedule) {
        System.out.println("Admin '" + getUsername() + "' memperbarui jadwal: " + schedule.toString());
    }

    public void removeSchedule(String scheduleId) {
        System.out.println("Admin '" + getUsername() + "' menghapus jadwal dengan ID: " + scheduleId);
    }
}