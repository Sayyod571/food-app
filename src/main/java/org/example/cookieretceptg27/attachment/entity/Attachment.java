package org.example.cookieretceptg27.attachment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.enums.FileType;
import org.example.cookieretceptg27.recipe_attachment.entity.Recipe_attachment;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Setter
@Entity
@Table(name = "'attachment'")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String file_name;
    @Column(nullable = false,unique = true)
    private String url;
    @Column(nullable = false,unique = true)
    private FileType fileType;
    @OneToMany(mappedBy = "attachment_id",fetch = FetchType.EAGER)
    private List<Recipe_attachment>recipeAttachments;
}
