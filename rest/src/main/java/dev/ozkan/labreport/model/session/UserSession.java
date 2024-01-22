package dev.ozkan.labreport.model.session;

import dev.ozkan.labreport.model.user.UserRole;

public class UserSession {
    private String userId;
    private String hospitalIdNumber;

    private UserRole userRole;

    public String getUserId() {
        return userId;
    }

    public UserSession setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getHospitalIdNumber() {
        return hospitalIdNumber;
    }

    public UserSession setHospitalIdNumber(String hospitalIdNumber) {
        this.hospitalIdNumber = hospitalIdNumber;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserSession setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }
}
