package dpbo.bimble;

public class Main {
    public static void main(String[] args) {
        Auth authService = new Auth();

        System.out.println("\n--- Silakan Login ---");

        String username = MissionUtil.getString("Masukkan username");
        String password = MissionUtil.getPassword("Masukkan password");

        User loggedInUser = authService.loginUser(username, password);

        if (loggedInUser != null) {
            System.out.println("Login berhasil! Selamat datang, " + loggedInUser.getUsername() + " (" + loggedInUser.getRole() + ")");
            loggedInUser.login();

            if (loggedInUser instanceof Admin) {
                Admin adminUser = (Admin) loggedInUser;
                System.out.println("Anda adalah Admin. Menampilkan menu admin...");

            } else if (loggedInUser instanceof Student) {
                Student studentUser = (Student) loggedInUser;
                System.out.println("Anda adalah Student. Menampilkan menu student...");
            }

        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }

        // MissionUtil.closeScanner();
    }
}