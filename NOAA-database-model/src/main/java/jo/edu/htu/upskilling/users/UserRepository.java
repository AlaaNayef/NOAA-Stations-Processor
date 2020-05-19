package jo.edu.htu.upskilling.users;

import java.util.List;

public interface UserRepository {

    void createTable();

    void insert(UserTransferObj user);

    void updatePassword(String userName,String newPassword);

    void updateStatus(String userName,int status);

    UserTransferObj findByUserName(String userName);

    List<UserTransferObj> AllUsers();
}
