package Repositories;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import entities.DatiSportivi;
import org.springframework.stereotype.Repository;

import java.beans.BeanProperty;
import java.util.List;
//import org.springframework.data.repository.CrudRepository;

@Repository
public interface AzureRepository extends CosmosRepository<DatiSportivi, String>{

    List<DatiSportivi> findBynomeUtente(String nomeUtente);

}
