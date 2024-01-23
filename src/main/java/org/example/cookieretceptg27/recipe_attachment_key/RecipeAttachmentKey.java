package org.example.cookieretceptg27.recipe_attachment_key;

import java.io.Serializable;
import java.util.Objects;

public class RecipeAttachmentKey implements Serializable {

    private Long attachmentId;
    private Long recipeId;

    // constructors, getters, and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeAttachmentKey that = (RecipeAttachmentKey) o;
        return Objects.equals(attachmentId, that.attachmentId) &&
                Objects.equals(recipeId, that.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachmentId, recipeId);
    }
}
