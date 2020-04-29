package jo.edu.htu.upskilling.users;

public class UserTransferObj {

    private String userName;
    private String name;
    private String email;
    private int status;
    private String password;

    public UserTransferObj(String userName, String name, String email, int status,String password) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.status = status;
        this.password=password;
    }

    public UserTransferObj() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }
}
