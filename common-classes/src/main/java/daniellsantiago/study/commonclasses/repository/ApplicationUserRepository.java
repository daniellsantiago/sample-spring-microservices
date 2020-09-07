package daniellsantiago.study.commonclasses.repository;

import daniellsantiago.study.commonclasses.model.ApplicationUser;

import java.util.ArrayList;
import java.util.List;

public class ApplicationUserRepository {
    private final List<ApplicationUser> users = new ArrayList<>();

    public ApplicationUser findByUsername(String username) {
        return users.stream()
                .filter(applicationUser -> applicationUser.getUsername().equalsIgnoreCase(username)).findFirst().get();
    }

    public void add(ApplicationUser user) {
        users.add(user);
    }
}
