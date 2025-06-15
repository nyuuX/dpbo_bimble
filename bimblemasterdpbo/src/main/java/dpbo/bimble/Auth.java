package dpbo.bimble;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Auth {

    private List<User> userDatabase;

    public Auth() {
        this.userDatabase = new ArrayList<>();
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        Admin defaultAdmin = new Admin("admin_default_01", "admin", "adminpass", null);

        if (findUserByUsername(defaultAdmin.getUsername()) == null) {
            this.userDatabase.add(defaultAdmin);
            System.out.println("Info: Default Admin user 'admin' telah dibuat.");
        }

        Student defaultStudent = new Student("student_default_01", "student", "studentpass");
        if (findUserByUsername(defaultStudent.getUsername()) == null) {
            this.userDatabase.add(defaultStudent);
            System.out.println("Info: Default Student user 'student' telah dibuat.");
        }
    }

    private User findUserByUsername(String username) {
        for (User user : userDatabase) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean registerUser(User user) {
        if (findUserByUsername(user.getUsername()) != null) {
            System.out.println("Registrasi gagal: Username '" + user.getUsername() + "' sudah ada.");
            return false;
        }
        userDatabase.add(user);
        System.out.println("User '" + user.getUsername() + "' berhasil diregistrasi sebagai '" + user.getRole() + "'. (Data di memori)");
        return true;
    }

    public User loginUser(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                System.out.println("User '" + username + "' berhasil login. (Dari data memori)");
                return user;
            } else {
                System.out.println("Login gagal: Password salah untuk user '" + username + "'.");
                return null;
            }
        }
        System.out.println("Login gagal: User '" + username + "' tidak ditemukan.");
        return null;
    }

    public void logoutUser(User user) {
        if (user != null) {
            System.out.println("User '" + user.getUsername() + "' sedang proses logout.");
            System.out.println("User '" + user.getUsername() + "' berhasil logout. (Status di memori)");
        }
    }

    public Optional<User> findUserById(String userId) {
        return userDatabase.stream()
                           .filter(u -> u.getUserId().equals(userId))
                           .findFirst();
    }

}