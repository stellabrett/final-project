package Cooking.School.Project.cookingSchool.controller;

import Cooking.School.Project.cookingSchool.Services.RecipeService;
import Cooking.School.Project.cookingSchool.Services.TagService;
import Cooking.School.Project.cookingSchool.entities.Recipe;
import Cooking.School.Project.cookingSchool.exceptions.RecipeNotFoundException;
import Cooking.School.Project.cookingSchool.restapi.DTO.RecipeIngredientDTO;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RecipeService recipeService;

    //--------------------------- Recipe


    //TODO lieber ins userService?
    @PostMapping("admin/addRecipe")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe) {
        try {
            recipeService.addRecipe(recipe);
            return new ResponseEntity<>("Rezept erfolgrich erstellt", HttpStatus.CREATED);
        } catch (DuplicateKeyException dke) {
            return new ResponseEntity<>(dke.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("admin/getAllRecipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        List<Recipe> recipes = recipeService.getAllRecipe();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/admin/recipe/{id}")
    public Recipe getRecipeById(@PathVariable Long recipeId){
        return recipeService.getRecipeById(recipeId);
    }

   @PutMapping("/admin/updateRecipe/{recipeId}")
    public ResponseEntity<?> updateRecipe(
           @PathVariable Long recipeId,
           @RequestBody Recipe updatedRecipe
           ){
        try {
            Recipe updated = recipeService.updateRecipe(recipeId, updatedRecipe);

            return  new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (RecipeNotFoundException rnfe){
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }

   }

}
