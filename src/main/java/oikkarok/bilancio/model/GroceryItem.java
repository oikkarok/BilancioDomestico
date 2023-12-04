package oikkarok.bilancio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("groceryitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItem {

	@Id
	private String id;
	private String name;
	private int quantity;
	private String category;

}