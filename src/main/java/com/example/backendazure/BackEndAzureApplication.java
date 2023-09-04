package com.example.backendazure;

import Configurations.CosmosDBConfiguration;
import Configurations.SecurityConfig;
import Controller.AzureDatabaseController;
import Controller.UserController;
import Repositories.AzureRepository;
import Repositories.UserRepository;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import entities.DatiSportivi;

import entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext; //appena aggiunta
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("Repositories")
@ComponentScan("Services")
@ComponentScan(basePackageClasses= UserController.class)
@ComponentScan(basePackageClasses= AzureDatabaseController.class)
@ComponentScan(basePackageClasses= SecurityConfig.class)
@EnableCosmosRepositories(basePackages="Repositories")
@EntityScan(basePackages="Entities")
@Import(CosmosDBConfiguration.class)
@CrossOrigin
public class BackEndAzureApplication implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(BackEndAzureApplication.class);



    @Autowired
    private final AzureRepository repo;

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CosmosTemplate template;

    public BackEndAzureApplication(CosmosTemplate templete, AzureRepository repo, UserRepository userRepo) {
        this.userRepo = userRepo;
        this.template=template;
        this.repo=repo;
    }



    public static void main(String[] args) {
        SpringApplication.run(BackEndAzureApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Creazione di un oggetto Dati da salvare nel database Cosmos DB
        DatiSportivi dati = new DatiSportivi();
        dati.setNomeUtente("Simone");
        dati.setDurata(36);
        dati.setTipoAttivita("Basket");
        dati.setNote("Ho vinto");

        //User user= new User("simo.outlook","ciao");
        //User userSalvato = userRepo.save(user);

        //DatiSportivi datisalvati = cosmosTemplate.insert("LazyHound",dati);
        //DatiSportivi datisalvati = repo.save(dati);
        //System.out.println(dati.toString());
        List<User> userPresi = userRepo.findByMail("simo.outlook");

        for(User user : userPresi){
            System.out.println(user);
        }

    }
}
