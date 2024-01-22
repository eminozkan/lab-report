package dev.ozkan.labreport.services.user;

import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.util.result.CrudResult;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    CrudResult switchUserIsEnabled(String userId);
}
