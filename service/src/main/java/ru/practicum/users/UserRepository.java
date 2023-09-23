package ru.practicum.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    void deleteById(long userId);
    Page<User> findByIdIn(Iterable ids, Pageable page);
    User getById(long id);
}
