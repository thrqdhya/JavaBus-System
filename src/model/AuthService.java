package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthService {
    private List<User> userDatabase = new ArrayList<>();

    // B. REGISTER
    public String register(String username, String email, String password) {
        // Cek apakah username sudah ada
        for (User u : userDatabase) {
            if (u.getUsername().equals(username)) return "Username sudah terpakai!";
        }

        // Simpan user (Password idealnya di-hash di sini)
        User newUser = new User(userDatabase.size() + 1, username, email, password);
        
        // D. VERIFIKASI (Generate OTP Sederhana)
        String otp = String.format("%04d", new Random().nextInt(10000));
        newUser.setVerificationCode(otp);
        
        userDatabase.add(newUser);
        return "Registrasi berhasil! Kode OTP kamu (simulasi email): " + otp;
    }

    // C. LOGIN
    public String login(String username, String password) {
        for (User u : userDatabase) {
            if (u.getUsername().equals(username)) {
                if (!u.isVerified()) return "Gagal: Akun belum diverifikasi!";
                if (u.getPassword().equals(password)) {
                    return "Login Berhasil! Selamat datang " + username;
                }
            }
        }
        return "Username atau Password salah!";
    }

    // D. VERIFY OTP
    public boolean verifyAccount(String username, String code) {
        for (User u : userDatabase) {
            if (u.getUsername().equals(username) && u.getVerificationCode().equals(code)) {
                u.setVerified(true);
                return true;
            }
        }
        return false;
    }
}
