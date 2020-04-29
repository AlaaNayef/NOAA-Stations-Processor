package jo.edu.htu.upskilling.users;

public class AuthenticationResult {

    private boolean authenticated;

    public AuthenticationResult(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
