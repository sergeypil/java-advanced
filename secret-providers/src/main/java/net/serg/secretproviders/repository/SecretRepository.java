package net.serg.secretproviders.repository;

import net.serg.secretproviders.entity.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretRepository extends JpaRepository<Secret, String> {
}