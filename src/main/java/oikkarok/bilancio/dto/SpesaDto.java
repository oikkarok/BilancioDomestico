package oikkarok.bilancio.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oikkarok.bilancio.model.TipoSpesa;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpesaDto {

    private String id;
    private TipoSpesa tipo;
    private double importo;
    private LocalDate data;
	
}
