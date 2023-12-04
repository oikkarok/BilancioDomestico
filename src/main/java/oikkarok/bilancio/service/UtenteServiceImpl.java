package oikkarok.bilancio.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oikkarok.bilancio.dto.SpesaDto;
import oikkarok.bilancio.dto.UtenteDto;
import oikkarok.bilancio.model.Spesa;
import oikkarok.bilancio.model.Utente;
import oikkarok.bilancio.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UtenteDto createUser(UtenteDto utenteDto) {
		Utente utente = modelMapper.map(utenteDto, Utente.class);
		Utente savedUtente = utenteRepository.save(utente);
		return modelMapper.map(savedUtente, UtenteDto.class);
	}

	@Override
	public List<UtenteDto> getAllUsers() {
		List<Utente> utenti = utenteRepository.findAll();
		return utenti.stream().map(utente -> modelMapper.map(utente, UtenteDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<UtenteDto> getAllSpese() {
		List<Utente> utenti = utenteRepository.findAll();
	    return utenti.stream()
	            .map(utente -> {
	                UtenteDto utenteDto = modelMapper.map(utente, UtenteDto.class);
	                utenteDto.setSpese(mapSpeseToSpeseDto(utente.getSpese()));
	                return utenteDto;
	            })
	            .collect(Collectors.toList());
	}
	
	private List<SpesaDto> mapSpeseToSpeseDto(List<Spesa> spese) {
	    return spese.stream()
	            .map(spesa -> modelMapper.map(spesa, SpesaDto.class))
	            .collect(Collectors.toList());
	}

	@Override
	public UtenteDto getUserById(String id) {
		Utente utente = utenteRepository.findById(id).orElse(null);
		return (utente != null) ? modelMapper.map(utente, UtenteDto.class) : null;
	}

	@Override
	public void addSpesa(String utenteId, SpesaDto spesaDto) {
		Utente utente = utenteRepository.findById(utenteId).orElse(null);
		if (utente != null) {
			Spesa spesa = modelMapper.map(spesaDto, Spesa.class);
			utente.getSpese().add(spesa);
			utenteRepository.save(utente);
		}
	}

	@Override
	public SpesaDto getSpesaById(String userId, String expenseId) {
		Utente user = utenteRepository.findById(userId).orElse(null);
		if (user != null) {
			Optional<Spesa> expense = user.getSpese().stream().filter(e -> e.getId().equals(expenseId)).findFirst();
			return expense.map(value -> modelMapper.map(value, SpesaDto.class)).orElse(null);
		}
		return null;
	}

	@Override
	public List<SpesaDto> getAllSpese(String userId) {
		Utente user = utenteRepository.findById(userId).orElse(null);
		return (user != null) ? user.getSpese().stream().map(expense -> modelMapper.map(expense, SpesaDto.class))
				.collect(Collectors.toList()) : Collections.emptyList();
	}

	@Override
	public List<SpesaDto> getSpeseByDate(String userId, LocalDate date) {
		Utente user = utenteRepository.findById(userId).orElse(null);
		return (user != null)
				? user.getSpese().stream().filter(expense -> expense.getData().isEqual(date))
						.map(expense -> modelMapper.map(expense, SpesaDto.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	public List<SpesaDto> getSpeseByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
		Utente user = utenteRepository.findById(userId).orElse(null);
		if (user != null) {
			return user.getSpese().stream()
					.filter(expense -> !expense.getData().isBefore(startDate) && !expense.getData().isAfter(endDate))
					.map(expense -> modelMapper.map(expense, SpesaDto.class))
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
    public SpesaDto getMaxSpesa() {	
		List<Spesa> allExpenses = utenteRepository.findAll().stream()
                .flatMap(user -> user.getSpese().stream())
                .collect(Collectors.toList());

        if (!allExpenses.isEmpty()) {
            return allExpenses.stream()
                    .max(Comparator.comparingDouble(Spesa::getImporto))
                    .map(expense -> modelMapper.map(expense, SpesaDto.class))
                    .orElse(null);
        }    
		return null;
    }
	
    @Override
    public SpesaDto getMaxSpesaByUserId(String userId) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null && !user.getSpese().isEmpty()) {
            return user.getSpese().stream()
                    .max(Comparator.comparingDouble(Spesa::getImporto))
                    .map(expense -> modelMapper.map(expense, SpesaDto.class))
                    .orElse(null);
        }       
        return null;
    }
	
	@Override
    public long countAllSpese() {
        return utenteRepository.findAll().stream()
                .mapToLong(user -> user.getSpese().size())
                .sum();
    }

    @Override
    public long countSpeseForUser(String userId) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        return (user != null) ? user.getSpese().size() : 0;
    }

    @Override
    public double averageAllSpese() {
        List<Double> allSpese = utenteRepository.findAll().stream()
                .flatMap(user -> user.getSpese().stream())
                .mapToDouble(Spesa::getImporto)
                .boxed()
                .collect(Collectors.toList());

        return calculateAverage(allSpese);
    }

    @Override
    public double averageSpeseForUser(String userId) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Double> userSpese = user.getSpese().stream()
                    .mapToDouble(Spesa::getImporto)
                    .boxed()
                    .collect(Collectors.toList());

            return calculateAverage(userSpese);
        }
        return 0;
    }

    @Override
    public double medianAllSpese() {
        List<Double> allSpese = utenteRepository.findAll().stream()
                .flatMap(user -> user.getSpese().stream())
                .mapToDouble(Spesa::getImporto)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Importi delle spese: " + allSpese);
        return calculateMedian(allSpese);
    }

    @Override
    public double medianSpeseForUser(String userId) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Double> userSpese = user.getSpese().stream()
                    .mapToDouble(Spesa::getImporto)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            return calculateMedian(userSpese);
        }
        return 0;
    }

    // Metodo di supporto per calcolare la media
    private double calculateAverage(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // Metodo di supporto per calcolare la mediana
    private double calculateMedian(List<Double> values) {
    	Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            int middle = size / 2;
            return (values.get(middle - 1) + values.get(middle)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }

    @Override
    public long countAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate) {
        return utenteRepository.findAll().stream()
                .mapToLong(user -> user.getSpese().size())
                .sum();
    }

    @Override
    public long countSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getSpese().stream()
                    .filter(spesa -> spesa.getData().isAfter(startDate) && spesa.getData().isBefore(endDate))
                    .count();
        }
        
        return 0;
    }

    @Override
    public double averageAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate) {
    	
        List<Double> allSpese = utenteRepository.findAll().stream()
                .flatMap(user -> user.getSpese().stream())
                .filter(spesa -> spesa.getData().isAfter(startDate) && spesa.getData().isBefore(endDate))
                .mapToDouble(Spesa::getImporto)
                .boxed()
                .collect(Collectors.toList());

        return calculateAverage(allSpese);
    }

    @Override
    public double averageSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Double> userSpese = user.getSpese().stream()
                    .filter(spesa -> spesa.getData().isAfter(startDate) && spesa.getData().isBefore(endDate))
                    .mapToDouble(Spesa::getImporto)
                    .boxed()
                    .collect(Collectors.toList());

            return calculateAverage(userSpese);
        }
        
        return 0;
    }

    @Override
    public double medianAllSpeseBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Double> allSpese = utenteRepository.findAll().stream()
                .flatMap(user -> user.getSpese().stream())
                .filter(spesa -> spesa.getData().isAfter(startDate) && spesa.getData().isBefore(endDate))
                .mapToDouble(Spesa::getImporto)
                .sorted()
                .boxed()
                .collect(Collectors.toList());

        return calculateMedian(allSpese);
    }

    @Override
    public double medianSpeseForUserBetweenDates(String userId, LocalDate startDate, LocalDate endDate) {
        Utente user = utenteRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Double> userSpese = user.getSpese().stream()
                    .filter(spesa -> spesa.getData().isAfter(startDate) && spesa.getData().isBefore(endDate))
                    .mapToDouble(Spesa::getImporto)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            return calculateMedian(userSpese);
        }
        
        return 0;
    }
    
}
