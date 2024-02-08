package net.serg.springfoundation.repository;

import net.serg.springfoundation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}