package Services;
import Repositories.AzureRepository;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import entities.DatiSportivi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class AzureDatabaseService{

    @Autowired
    private AzureRepository azureRepository;

    @Value("${spring.cloud.azure.cosmos.uri}")
    private String uri;

    @Value("${spring.cloud.azure.cosmos.key}")
    private String key;

    @Value("${spring.cloud.azure.cosmos.database}")
    private String dbName;

    private static final String containerName = "LazyHound";

    @Autowired
    public AzureDatabaseService(AzureRepository azureRepository) {
        this.azureRepository = azureRepository;
    }

    @Transactional(readOnly = false)
    public void addDataToAzureDatabase(DatiSportivi dati) {
        System.out.println("AAAAAAAAAAAAAAAA SEI GAYYYYYYYY");
        CosmosClientBuilder clientBuilder = new CosmosClientBuilder().endpoint(uri).key(key);
        CosmosClient cosmosClient = clientBuilder.buildClient();
        CosmosContainer container = ((CosmosClient) cosmosClient).getDatabase(dbName).getContainer(containerName);
        CosmosItemResponse<DatiSportivi> response = container.createItem(dati);
        azureRepository.save(dati);

    }


    public List<DatiSportivi> getDatiSportivisBynomeUtente(String nomeUtente) {

        return azureRepository.findBynomeUtente(nomeUtente);
    }


}
