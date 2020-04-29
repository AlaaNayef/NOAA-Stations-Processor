package jo.edu.htu.upskilling.users;

import java.util.List;

public class ViewUsersResult {
    List<UserTransferObj> allUsers;

    public ViewUsersResult(List<UserTransferObj> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserTransferObj> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserTransferObj> allUsers) {
        this.allUsers = allUsers;
    }


}
