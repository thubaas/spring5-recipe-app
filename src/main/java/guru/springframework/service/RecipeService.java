package guru.springframework.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;

public interface RecipeService {
	
	Set<Recipe> getRecipes();

}
