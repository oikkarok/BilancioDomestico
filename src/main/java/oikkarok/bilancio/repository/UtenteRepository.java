package oikkarok.bilancio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import oikkarok.bilancio.model.Utente;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {
    
}

