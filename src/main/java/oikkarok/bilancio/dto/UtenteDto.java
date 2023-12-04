package oikkarok.bilancio.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteDto {
	
    private String id;
    private String nome;
    private String cognome;
    private List<SpesaDto> spese;

}
