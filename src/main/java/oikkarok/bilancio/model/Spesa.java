package oikkarok.bilancio.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spesa {
   
	private String id;
    private TipoSpesa tipo;
    private double importo;
    private LocalDate data;
}
