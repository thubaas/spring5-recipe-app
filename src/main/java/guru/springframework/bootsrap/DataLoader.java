package guru.springframework.bootsrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		recipeRepository.saveAll(getRecipes());
		
	}

	
	private List<Recipe> getRecipes(){
		
		log.debug("beginning Data Loading");

		List<Recipe> recipes = new ArrayList<>(2);
		
		// get UoMs
		Optional<UnitOfMeasure> eachOptional = unitOfMeasureRepository.findByDescription("Each");
		if(!eachOptional.isPresent()) {
			throw new RuntimeException("Expected UoM not found");
		}
		
		Optional<UnitOfMeasure> tableSpoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
		if(!tableSpoonOptional.isPresent()) {
			throw new RuntimeException("Expected UoM not found");
		}
		
		Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		if(!teaspoonOptional.isPresent()) {
			throw new RuntimeException("Expected Uom not found");
		}
		
		Optional<UnitOfMeasure> dashOptional = unitOfMeasureRepository.findByDescription("Dash");
		if(!dashOptional.isPresent()) {
			throw new RuntimeException("Expected Uom not found");
		}
		
		Optional<UnitOfMeasure> pintOptional = unitOfMeasureRepository.findByDescription("Pint");
		if(!pintOptional.isPresent()) {
			throw new RuntimeException("Expected Uom not found");
		}
		
		Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");
		if(!cupOptional.isPresent()) {
			throw new RuntimeException("Expected Uom not found");
		}
		
		
		// get UoM
		UnitOfMeasure eachUom = eachOptional.get();
		UnitOfMeasure tablespoonUom = tableSpoonOptional.get();
		UnitOfMeasure teaspoonUom = teaspoonOptional.get();
		UnitOfMeasure dashUom = dashOptional.get();
		UnitOfMeasure pintUom = pintOptional.get();
		UnitOfMeasure cupUom = cupOptional.get();
		
		//get Categories optional
		Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
		if(!americanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
		if(!mexicanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Category americanCategory = americanCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();
		
		//Yummy Guac
		Recipe guacRecipe = new Recipe();
		guacRecipe.setDescription("Perfect Guacamole");
		guacRecipe.setPrepTime(10);
		guacRecipe.setCookTime(0);
		guacRecipe.setDifficulty(Difficulty.EASY);
		guacRecipe.setDirections("1 Cut the avocado:"
				+ "\n"
				+ "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a "
				+ "blunt knife and scoop out the flesh with a spoon"
				+ "\n"
				+ "2 Mash the avocado flesh:"
				+ "\n"
				+ "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should "
				+ "be a little chunky.) "
				+ "\n"
				+ "3 Add remaining ingredients to taste:"
				+ "\n"
				+ "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will "
				+ "provide some balance to the richness of the avocado and will help delay the "
				+ "avocados from turning brown."
				+ "\n"
				+ "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary"
				+ " individually in their spiciness. So, start with a half of one chili pepper and"
				+ " add more to the guacamole to your desired degree of heat."
				+ "\n"
				+ "Remember that much of this is done to taste because of the variability in the"
				+ " fresh ingredients. Start with this recipe and adjust to your taste."
				+ "\n"
				+ "4 Serve immediately:"
				+ "\n"
				+ "If making a few hours ahead, place plastic wrap on the surface of the guacamole"
				+ " and press down to cover it to prevent air reaching it. (The oxygen in the air "
				+ "causes oxidation which will turn the guacamole brown.) \n");
		
		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes("The word \"guacamole\" and the dip, are both originally from Mexico,"
				+ " where avocados have been cultivated for thousands of years. The name is derived "
				+ "from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");
		
		guacRecipe.setNotes(guacNotes);
		
		guacRecipe.addIngredient(new Ingredient("ripe avocado", new BigDecimal(2), eachUom));
		guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(0.5), teaspoonUom));
		guacRecipe.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(2), 
				tablespoonUom));
		guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", 
				new BigDecimal(2), tablespoonUom));
		guacRecipe.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds "
				+ "removed, minced", new BigDecimal(2), eachUom));
		guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoonUom));
		guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", 
				new BigDecimal(2), dashUom));
		guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped",
				new BigDecimal(0.5), eachUom));
		
		guacRecipe.getCategories().add(americanCategory);
		guacRecipe.getCategories().add(mexicanCategory);
		
		// add recipe to return list
		recipes.add(guacRecipe);
		
		// Yummy Tacos
		Recipe tacosRecipe = new Recipe();
		tacosRecipe.setDescription("Spic Grilled Chicken Taco");
		tacosRecipe.setCookTime(9);
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setDifficulty(Difficulty.MODERATE);
		
		tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat"
				+ "\n"
				+ "2 Make the marinade and coat the chicken:"
				+ "\n"
				+ "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic"
				+ " and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add "
				+ "the chicken to the bowl and toss to coat all over."
				+ "\n"
				+ "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
				+ "\n"
				+ "3 Grill the chicken:"
				+ "\n"
				+ "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into "
				+ "the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 "
				+ "minutes.\n"
				+ "4 Warm the tortillas:"
				+ "\n"
				+ "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As"
				+ " soon as you see pockets of the air start to puff up in the tortilla, turn it with "
				+ "tongs and heat for a few seconds on the other side."
				+ "\n"
				+ "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
				+ "5  Assemble the tacos:"
				+ "\n"
				+ "Slice the chicken into strips. On each tortilla, place a small handful of arugula."
				+ "Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. "
				+ "Drizzle with the thinned sour cream. Serve with lime wedges");
		
		Notes tacosNotes = new Notes();
		tacosNotes.setRecipeNotes(" We have a family motto and it is this: Everything goes better in a "
				+ "tortilla."
				+ "\n"
				+ "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy "
				+ "dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma "
				+ "of tortillas heating in a hot pan on the stove comes wafting through the house.\n"
				+ "\n"
				+ "Today's tacos are more purposeful – a deliberate meal instead of a secretive midnight"
				+ " snack! ");
		
		tacosRecipe.setNotes(tacosNotes);
		
		tacosRecipe.addIngredient(new Ingredient("Ancho chilli powder", new BigDecimal(2), tablespoonUom));
		tacosRecipe.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1), teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Dried cumin", new BigDecimal(1), teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(0.5), teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Clove of garlic, chopped", new BigDecimal(1),eachUom));
		tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), 
				tablespoonUom));
		tacosRecipe.addIngredient(new Ingredient("Freshly squeezed orange juice", new BigDecimal(3), 
				tablespoonUom));
		tacosRecipe.addIngredient(new Ingredient("Olive oil", new BigDecimal(2), tablespoonUom));
		tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), 
				tablespoonUom));
		tacosRecipe.addIngredient(new Ingredient("Small corn totillasr", new BigDecimal(8), eachUom));
		tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom));
		
		tacosRecipe.getCategories().add(americanCategory);
		tacosRecipe.getCategories().add(mexicanCategory);
		
		recipes.add(tacosRecipe);
		
		log.debug("Finished Data loading");

		return recipes;
		
	}
	

}
