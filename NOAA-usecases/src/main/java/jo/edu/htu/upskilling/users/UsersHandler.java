package jo.edu.htu.upskilling.users;

public interface UsersHandler {

    AuthenticationResult isAuthenticated(AuthenticationRequest request);

    void addUser(AddUserRequest request);

    void changePassword(ChangePassReq request);

    void changeStatus(ChangeStatusReq request);

    ViewUsersResult viewUsers();
}
