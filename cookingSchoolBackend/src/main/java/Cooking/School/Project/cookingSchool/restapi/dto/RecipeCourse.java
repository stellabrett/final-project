package Cooking.School.Project.cookingSchool.restapi.dto;

import Cooking.School.Project.cookingSchool.entities.Ingredient;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCourse {

    //@Setter(AccessLevel.NONE)
    private Long recipeId;

    private String title;

    private String description;

    private int difficulty;

    private int preparation;

    private Set<Long> courseIds;

    private Set<Ingredient> ingredients;


}
