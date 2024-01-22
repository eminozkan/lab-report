package dev.ozkan.labreport.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user")
public class User extends CustomUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "hospital_id_number", length = 7)
    private String hospitalIdNumber;

    @JsonIgnore
    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "full_name")
    private String fullName;


    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return hospitalIdNumber;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getUserId() {
        return userId;
    }

    public String getHospitalIdNumber() {
        return hospitalIdNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public static class Builder {
        private final User userToBuild;

        public Builder() {
            userToBuild = new User();
        }

        public Builder(User user) {
            userToBuild = user;
        }

        public Builder userId(String userId) {
            userToBuild.userId = userId;
            return this;
        }

        public Builder hospitalIdNumber(String hospitalIdNumber) {
            userToBuild.hospitalIdNumber = hospitalIdNumber;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            userToBuild.passwordHash = passwordHash;
            return this;
        }

        public Builder fullName(String fullName) {
            userToBuild.fullName = fullName;
            return this;
        }


        public Builder role(UserRole role) {
            userToBuild.role = role;
            return this;
        }

        public Builder enabled(boolean isEnabled) {
            userToBuild.isEnabled = isEnabled;
            return this;
        }

        public User build() {
            return userToBuild;
        }
    }
}
