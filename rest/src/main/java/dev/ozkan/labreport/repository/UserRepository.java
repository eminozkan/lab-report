package dev.ozkan.labreport.repository;

import dev.ozkan.labreport.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    /**
     * Verilen Hastahane kimlik numarası ile eşleşen kullanıcı kaydını getirir.
     * @return kullanıcı var ise Optional içerisinde kullanıcıyı, yok ise Optional.empty
     */
    Optional<User> getUserByHospitalIdNumber(String hospitalIdNumber);

    /**
     * Verilen kullanıcı id'si ile eşleşen kullanıcı kaydını getirir.
     * @return kullanıcı var ise Optional içerisinde kullanıcıyı, yok ise Optional.empty
     */
    Optional<User> getByUserId(String reportWriterId);
}
