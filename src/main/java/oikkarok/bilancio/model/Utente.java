package oikkarok.bilancio.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utente {
	
    private String id;
    private String nome;
    private String cognome;
    
    @Field("spese")
    private List<Spesa> spese;
}

