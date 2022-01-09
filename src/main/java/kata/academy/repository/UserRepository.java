package kata.academy.repository;

import kata.academy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserById(Long id);

    boolean existsById(Long id);
}
