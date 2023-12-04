package oikkarok.bilancio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import oikkarok.bilancio.dto.SpesaDto;
import oikkarok.bilancio.dto.UtenteDto;
import oikkarok.bilancio.service.UtenteService;

@RestController
@RequestMapping("/api")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@PostMapping("/creaUtente")
	public UtenteDto createUser(@RequestBody UtenteDto utenteDto) {
		return utenteService.createUser(utenteDto);
	}

	@GetMapping("/utenti")
	public List<UtenteDto> getAllUsers() {
		return utenteService.getAllUsers();
	}

	@GetMapping("/utente/{id}")
	public UtenteDto getUserById(@PathVariable String id) {
		return utenteService.getUserById(id);
	}

	@PostMapping("/utente/{utenteId}/aggiungiSpesa")
	public void addSpesa(@PathVariable String utenteId, @RequestBody SpesaDto spesaDto) {
		utenteService.addSpesa(utenteId, spesaDto);
	}

	@GetMapping("/utente/{userId}/spesa/{expenseId}")
	public SpesaDto getSpesaById(@PathVariable String userId, @PathVariable String expenseId) {
		return utenteService.getSpesaById(userId, expenseId);
	}

	@GetMapping("/utente/{userId}/spese")
	public List<SpesaDto> getAllSpese(@PathVariable String userId) {
		return utenteService.getAllSpese(userId);
	}

	@GetMapping("/utente/{userId}/spese/data")
	public List<SpesaDto> getSpeseByDate(@PathVariable String userId, @RequestParam LocalDate date) {
	    return utenteService.getSpeseByDate(userId, date);
	}

	@GetMapping("/utente/{userId}/spese/dateRange")
	public List<SpesaDto> getSpeseByDateRange(@PathVariable String userId, 
	                                          @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
	    return utenteService.getSpeseByDateRange(userId, startDate, endDate);
	}
	
	@GetMapping("/utenti/spese")
	public List<UtenteDto> getAllSpese() {
		return utenteService.getAllSpese();
	}
	
	@GetMapping("/spese/max")
	public SpesaDto getMaxSpesa() {
		return utenteService.getMaxSpesa();
	}
	
    @GetMapping("/{userId}/expenses/max")
    public SpesaDto getMaxSpesaByUserId(@PathVariable String userId) {
        return utenteService.getMaxSpesaByUserId(userId);
    }

	@GetMapping("/spese/countAll")
	public long countAllSpese() {
		return utenteService.countAllSpese();
	}

	@GetMapping("/utente/{userId}/spese/count")
	public long countSpeseForUser(@PathVariable String userId) {
		return utenteService.countSpeseForUser(userId);
	}

	@GetMapping("/spese/averageAll")
	public double averageAllSpese() {
		return utenteService.averageAllSpese();
	}

	@GetMapping("/utente/{userId}/spese/average")
	public double averageSpeseForUser(@PathVariable String userId) {
		return utenteService.averageSpeseForUser(userId);
	}

	@GetMapping("/spese/medianAll")
	public double medianAllSpese() {
		return utenteService.medianAllSpese();
	}

	@GetMapping("/utente/{userId}/spese/median")
	public double medianSpeseForUser(@PathVariable String userId) {
		return utenteService.medianSpeseForUser(userId);
	}

	@GetMapping("/spese/countBetweenDates")
	public long countAllSpeseBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		return utenteService.countAllSpeseBetweenDates(startDate, endDate);
	}

	@GetMapping("/utente/{userId}/spese/countBetweenDates")
	public long countSpeseForUserBetweenDates(@PathVariable String userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return utenteService.countSpeseForUserBetweenDates(userId, startDate, endDate);
	}

	@GetMapping("/spese/averageBetweenDates")
	public double averageAllSpeseBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		return utenteService.averageAllSpeseBetweenDates(startDate, endDate);
	}

	@GetMapping("/utente/{userId}/spese/averageBetweenDates")
	public double averageSpeseForUserBetweenDates(@PathVariable String userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return utenteService.averageSpeseForUserBetweenDates(userId, startDate, endDate);
	}

	@GetMapping("/spese/medianBetweenDates")
	public double medianAllSpeseBetweenDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		return utenteService.medianAllSpeseBetweenDates(startDate, endDate);
	}

	@GetMapping("/utente/{userId}/spese/medianBetweenDates")
	public double medianSpeseForUserBetweenDates(@PathVariable String userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return utenteService.medianSpeseForUserBetweenDates(userId, startDate, endDate);
	}

}
