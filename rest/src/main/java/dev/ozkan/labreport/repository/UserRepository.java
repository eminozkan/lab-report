package dev.ozkan.labreport.repository;

import dev.ozkan.labreport.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> getUserByHospitalIdNumber(String hospitalIdNumber);

    Optional<User> getByUserId(String reportWriterId);
}
