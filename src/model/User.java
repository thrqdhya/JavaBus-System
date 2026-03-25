package model;

import java.time.LocalDateTime;

public class User {
    // A. Atribut sesuai spesifikasi tugas
    private int id;
    private String username;
    private String email;
    private String password; // Disimpan dalam bentuk Hash nanti
    private boolean isVerified;
    private LocalDateTime createdAt;
    private String verificationCode; // Untuk menyimpan kode OTP

    // B. Constructor
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified = false; // Default: belum verifikasi
        this.createdAt = LocalDateTime.now();
    }

    // C. Getter dan Setter (Agar bisa diakses oleh AuthService dan Repository)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    // D. toString untuk mempermudah pengecekan saat Testing (Hafta 4)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isVerified=" + isVerified +
                ", createdAt=" + createdAt +
                ", otp='" + verificationCode + '\'' +
                '}';
    }
}
