package org.example.cookieretceptg27.recipe_attachment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.recipe_attachment_key.RecipeAttachmentKey;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Recipe_attachment {

    @Id
    @ManyToOne
    private Attachment attachment_id;

    @Id
    @ManyToOne
    private Recipe recipe_id;
    @ManyToOne(optional = false)
    private Recipe recipes;


}
