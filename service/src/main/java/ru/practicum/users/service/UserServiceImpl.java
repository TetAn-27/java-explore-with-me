package ru.practicum.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.UserRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.model.UserDto;
import ru.practicum.users.model.UserMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<User> pageRequest;
            if (ids == null) {
                pageRequest = userRepository.findAll(page);
            } else {
                pageRequest = userRepository.findByIdIn(ids, page);
            }
            pageRequest.getContent().forEach(ItemRequest -> {
            });
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            return UserMapper.toListUserDto(pageRequest.getContent());
        } while (page != null);
    }

    @Override
    public Optional<UserDto> createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        log.debug("Пользователь создан: {}", user);
        return Optional.of(UserMapper.toUserDto(userRepository.save(user)));
    }

    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (DataAccessException ex) {
            log.error("User с ID {} не был найден", userId);
            throw new NotFoundException("User с таким Id не был найден");
        }
    }
}
