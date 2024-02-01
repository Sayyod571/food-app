package org.example.cookieretceptg27.user;

import org.example.cookieretceptg27.common.repository.GenericSpecificationRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericSpecificationRepository<User, UUID> {
    Optional<User>findUserByEmail(String email);
    User findByEmail(String email);
    List<User> findAll();




}
