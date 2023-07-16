package com.example.firstsite.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Credentials extends BaseEntity{
    private String login;
    private String password;
    private int userId;

    private Credentials() {
        super();
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Credentials that = (Credentials) o;

        if (userId != that.userId) return false;
        if (!Objects.equals(login, that.login)) return false;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credentials{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }

    public static Credentials.Builder newBuilder() {
        return new Credentials().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Credentials.Builder setId(int id) {
            Credentials.super.setId(id);
            return this;
        }

        public Credentials.Builder setLogin(String login) {
            Credentials.this.login = login;
            return this;
        }

        public Credentials.Builder setPassword(String password) {
            Credentials.this.password = password;
            return this;
        }

        public Credentials.Builder setUserId(int userId) {
            Credentials.this.userId = userId;
            return this;
        }

        public Credentials build() {
            return Credentials.this;
        }

    }
}
