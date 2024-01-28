package org.example.cookieretceptg27.attachment;

import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

//    Optional<Attachment> findByUserId(UUID userId);

}
