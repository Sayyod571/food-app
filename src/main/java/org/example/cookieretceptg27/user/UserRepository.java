package org.example.cookieretceptg27.user;

import org.example.cookieretceptg27.common.repository.GenericSpecificationRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericSpecificationRepository<User, UUID> {
    Optional<User>findUserByEmail(String email);
}
