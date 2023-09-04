package Repositories;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import entities.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {
    List<User> findByMail(String mail);
}
