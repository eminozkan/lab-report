package dev.ozkan.labreport.services.user;

import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.model.user.UserRole;
import dev.ozkan.labreport.repository.UserRepository;
import dev.ozkan.labreport.util.result.CrudResult;
import dev.ozkan.labreport.util.result.OperationFailureReason;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().filter(user -> user.getRole() != UserRole.ROLE_MANAGER).toList();
    }

    @Override
    public CrudResult switchUserIsEnabled(String userId) {
        var userOptional = userRepository.getByUserId(userId);
        if (userOptional.isEmpty()) {
            return CrudResult.failed(OperationFailureReason.NOT_FOUND, "User not found");
        }
        var userFromDb = userOptional.get();
        var updatedUser = new User.Builder(userFromDb)
                .enabled(!userFromDb.isEnabled())
                .build();

        userRepository.save(updatedUser);
        return CrudResult.success();
    }
}
