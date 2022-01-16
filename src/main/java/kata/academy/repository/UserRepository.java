package kata.academy.repository;

import kata.academy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByLogin(String login);

    User findUserById(Long id);

    void deleteById(Long id);
}
