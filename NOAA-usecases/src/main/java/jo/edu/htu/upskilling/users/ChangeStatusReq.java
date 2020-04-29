package jo.edu.htu.upskilling.users;

public class ChangeStatusReq {

    private int status;
    private String userName;

    public ChangeStatusReq(int status, String userName) {
        this.status = status;
        this.userName = userName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }
}
