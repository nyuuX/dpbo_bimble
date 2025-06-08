package dpbo.bimble;

import java.util.List;

public class Admin extends User {
    private List<User> userDatabase; 

    public Admin(String userId, String username, String password, String role) {
        super(userId, username, password, role);
        this.userDatabase = null; 
    }
    
    public void setUserDatabase(List<User> userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void viewRegistrations() {
        if (userDatabase == null) {
            System.out.println("Error: User database not set for Admin.");
            return;
        }
        System.out.println("Menampilkan seluruh data siswa: ");
        for (User user : userDatabase) {
            if (user instanceof Student)
                System.out.println(user);
        }
    }

    public void confirmRegistrations() {
        if (userDatabase == null) {
            System.out.println("Error: User database not set for Admin.");
            return;
        }
        for (User user : userDatabase) {
            if (user instanceof Student) {
                // Logic to confirm registration (e.g., change a status in the Student object)
                // For example: ((Student) user).setRegistrationConfirmed(true);
            }
        }
        System.out.println("Registrations confirmation logic would go here.");
    }

    public void confirmPayment(Payment payment) {
        if (payment != null) {
            payment.setStatus("Sukses");
            System.out.println("Pembayaran dengan ID " + payment.getIdPayment() + " telah dikonfirmasi.");
        } else {
            System.out.println("Data pembayaran tidak ditemukan.");
        }
    }

    public void addSchedule(Schedule schedule) {
        System.out.println("Jadwal berhasil ditambahkan.");
    }

    public void updateSchedule() {
        System.out.println("Jadwal berhasil diperbarui.");
    }

    public void removeSchedule() {
        System.out.println("Jadwal berhasil dihapus.");
    }

    @Override
    public void login() {
        System.out.println("Akun dengan username '" + getUsername() + "' telah berhasil login sebagai Admin");
    }

    @Override
    public void logout() {
        System.out.println("Admin dengan username '" + getUsername() + "' telah logout");
    }

    @Override
    public void register() {
        System.out.println("Admin dengan username '" + getUsername() + "' telah berhasil melakukan registerasi");
    }
}