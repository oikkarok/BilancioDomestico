package oikkarok.bilancio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import oikkarok.bilancio.model.GroceryItem;
import oikkarok.bilancio.repository.ItemRepository;

@Slf4j
public class ItemService {

	@Autowired
	ItemRepository groceryItemRepo;

	// Print details in readable form
	private String getItemDetails(GroceryItem item) {

		String details = ("Item Name: " + item.getName() 
				+ ", \nQuantity: " + item.getQuantity() 
				+ ", \nItem Category: "
				+ item.getCategory());

		return details;
	}

	// CREATE
	public void createGroceryItems() {
		System.out.println("Data creation started...");
		log.info("Data creation started...");
		groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
		groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
		groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
		groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
		groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
		System.out.println("Data creation complete...");
		log.info("Data creation complete...");
	}

	// READ
	// 1. Show all the data
	public void showAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
	}

	// 2. Get item by name
	public void getGroceryItemByName(String name) {
		System.out.println("Getting item by name: " + name);
		GroceryItem item = groceryItemRepo.findItemByName(name);
		System.out.println(getItemDetails(item));
	}

	// 3. Get name and quantity of a all items of a particular category
	public void getItemsByCategory(String category) {
		System.out.println("Getting items for the category " + category);
		List<GroceryItem> list = groceryItemRepo.findAll(category);
		list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
	}

	// 4. Get count of documents in the collection
	public void findCountOfGroceryItems() {
		long count = groceryItemRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	public void updateCategoryName(String category) {

		// Change to this new value
		String newCategory = "munchies";

		// Find all the items with the category snacks
		List<GroceryItem> list = groceryItemRepo.findAll(category);

		list.forEach(item -> {
			// Update the category in each document
			item.setCategory(newCategory);
		});

		// Save all the items in database
		List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);

		if (itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");

	}
	
	// DELETE
    public void deleteGroceryItem(String id) {
        groceryItemRepo.deleteById(id);
        System.out.println("Item with id " + id + " deleted...");
    }

}
