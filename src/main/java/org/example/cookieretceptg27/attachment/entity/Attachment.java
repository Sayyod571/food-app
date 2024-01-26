package org.example.cookieretceptg27.attachment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.recipe_attachment.entity.Recipe_attachment;
import org.example.cookieretceptg27.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "'attachment'")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String file_name;

    @Column(nullable = false)
    private String url;

    private String fileType;

    private LocalDateTime uploadTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "attachment_id",fetch = FetchType.EAGER)
    private List<Recipe_attachment>recipeAttachments;
}
