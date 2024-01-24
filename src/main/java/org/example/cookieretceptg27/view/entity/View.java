package org.example.cookieretceptg27.view.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.recipe_attachment_key.RecipeAttachmentKey;
import org.example.cookieretceptg27.user.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class View {
    @Id
    @ManyToOne
    private User user_id;

    @Id
    @ManyToOne
    private Recipe recipe_id;
    @ManyToOne(optional = false)
    private User users;


}
