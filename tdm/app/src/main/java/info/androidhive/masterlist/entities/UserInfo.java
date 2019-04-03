package info.androidhive.masterlist.entities;

public class UserInfo {
    private String name;
    private long facebookId;
    private String email;
    private String password;

    public UserInfo(String name, long facebookId, String email, String password) {
        this.name = name;
        this.facebookId = facebookId;
        this.email = email;
        this.password = password;
    }

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(long facebookId) {
        this.facebookId = facebookId;
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
}
