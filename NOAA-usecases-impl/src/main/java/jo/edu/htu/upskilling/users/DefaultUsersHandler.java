package jo.edu.htu.upskilling.users;

public class DefaultUsersHandler implements UsersHandler {

    UserRepository repository;

    public DefaultUsersHandler(UserRepository repository) {
        this.repository = repository;

    }

    @Override
    public AuthenticationResult isAuthenticated(AuthenticationRequest request) {
        try {
            repository.findByUserName(request.getUserName());
            return new AuthenticationResult(true);
        } catch (NullPointerException exception) {
            return new AuthenticationResult(false);
        }
    }

    @Override
    public void addUser(AddUserRequest request) {
        repository.insert(request.getAddedUser());
    }

    @Override
    public void changePassword(ChangePassReq request) {
        repository.updatePassword(request.getUserName(), request.getNewPassword());
    }

    @Override
    public void changeStatus(ChangeStatusReq request) {
        repository.updateStatus(request.getUserName(), request.getStatus());
    }

    @Override
    public ViewUsersResult viewUsers() {
        return new ViewUsersResult(repository.AllUsers());
    }
}
