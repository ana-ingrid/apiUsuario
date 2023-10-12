package br.com.cadastro.enums;

public enum UserRole {

    ADMIN("admin"),
    USER("user");
    private String Role;

    UserRole(String role) {
        this.Role = role;
    }

    public String getRole() {
        return Role;
    }

}
