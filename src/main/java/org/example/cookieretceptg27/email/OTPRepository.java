package org.example.cookieretceptg27.email;

import org.example.cookieretceptg27.email.entity.OTP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends CrudRepository<OTP, String> {
}
