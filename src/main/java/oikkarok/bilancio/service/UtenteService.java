package oikkarok.bilancio.service;

import java.time.LocalDate;
import java.util.List;

import oikkarok.bilancio.dto.*;

public interface UtenteService {
	
    UtenteDto createUser(UtenteDto utente);
    List<UtenteDto> getAllUsers();
    UtenteDto getUserById(String id);
    void addSpesa(String utenteId, SpesaDto spesa);
	SpesaDto getSpesaById(String userId, String expenseId);
	List<SpesaDto> getAllSpese(String userId);
	List<SpesaDto> getSpeseByDate(String userId, LocalDate date);
	List<SpesaDto> getSpeseByDateRange(String userId, LocalDate startDate, LocalDate endDate);
	double medianSpeseForUser(String userId);
	double medianAllSpese();
	double averageSpeseForUser(String userId);
	double averageAllSpese();
	long countSpeseForUser(String userId);
	long countAllSpese();
	double medianSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate);
	double medianAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate);
	double averageSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate);
	double averageAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate);
	long countSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate);
	long countAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate);
	List<UtenteDto> getAllSpese();
	SpesaDto getMaxSpesa();
	SpesaDto getMaxSpesaByUserId(String userId);
	
}

