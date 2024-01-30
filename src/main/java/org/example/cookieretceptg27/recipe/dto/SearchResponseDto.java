package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.attachment.entity.Attachment;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {
    private UUID id;
    private String name;
    private Attachment recipeAttachments;
    private String userName;
    private double stars;
}
