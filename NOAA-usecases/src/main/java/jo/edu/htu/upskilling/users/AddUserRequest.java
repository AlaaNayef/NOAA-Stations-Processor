package jo.edu.htu.upskilling.users;

public class AddUserRequest {
   private UserTransferObj addedUser;

    public AddUserRequest(UserTransferObj addedUser) {
        this.addedUser = addedUser;
    }

    public void setAddedUser(UserTransferObj addedUser) {
        this.addedUser = addedUser;
    }

    public UserTransferObj getAddedUser() {
        return addedUser;
    }
}
