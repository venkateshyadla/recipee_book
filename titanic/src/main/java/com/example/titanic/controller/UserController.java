package com.example.titanic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.titanic.entity.Recipe;
import com.example.titanic.repository.RecipeRepository;

@Controller
public class UserController {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @GetMapping("/")
    public String welcomePage(Model model){
        return "welcome";
    }

    @GetMapping("/opt")
    public String homePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "home";
    }

    @PostMapping("/add")
    public String addRecipe(@ModelAttribute Recipe recipe) {
        recipeRepository.save(recipe);
        return "redirect:/opt";
    }
    
    @GetMapping("/recipes/{name}")
    public String getRecipeByName(@PathVariable String name, Model model) {
        Recipe recipe = recipeRepository.findByName(name)
                              .orElseThrow(() -> new RuntimeException("Recipe not found"));
        model.addAttribute("recipe", recipe);
        return "recipe_details";
    }
    @GetMapping("/recipes") // New endpoint to fetch all recipes
    @ResponseBody // Indicates that the return value should be written directly to the HTTP response body
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    @ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "error"; // This should match the name of your error page
    }
}
}
