package jo.edu.htu.upskilling.users;

public class ChangePassReq {
    private String newPassword;
    private String oldPassword;
    private String userName;

    public ChangePassReq(String newPassword, String oldPassword, String userName) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.userName = userName;
    }

    public ChangePassReq() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
