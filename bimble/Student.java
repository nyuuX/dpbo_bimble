package dpbo.bimble;

public class Student extends User {

    public Student(String userId, String username, String password) {
        super(userId, username, password, "STUDENT");
    }

    @Override
    public void login() {
        System.out.println("Student '" + getUsername() + "' melakukan tindakan spesifik setelah login.");
        System.out.println("Contoh: Menampilkan halaman utama student.");
    }

    @Override
    public void logout() {
        System.out.println("Student '" + getUsername() + "' melakukan tindakan spesifik saat logout.");
    }

    @Override
    public void register() {
        System.out.println("Student '" + getUsername() + "' sedang dalam proses 'Registrasi'.");
        System.out.println("Contoh: Memvalidasi data pendaftaran khusus student.");
    }
}