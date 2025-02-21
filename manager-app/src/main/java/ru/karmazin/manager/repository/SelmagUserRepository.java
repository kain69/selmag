package ru.karmazin.manager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.karmazin.manager.entity.SelmagUser;

import java.util.Optional;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {

    Optional<SelmagUser> findByUsername(String username);
}
