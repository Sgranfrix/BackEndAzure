package Controller;
import Services.AzureDatabaseService;
import Services.UserService;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import entities.DatiSportivi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api/datiSportivi")
public class AzureDatabaseController {

    @Autowired
    private AzureDatabaseService azureDatabaseService; //qui mi istanzio il servizio

    @Autowired
    private BlobServiceClient blobServiceClient;

    @ResponseStatus(code= HttpStatus.OK)
    @PostMapping("/aggiungi")
    public ResponseEntity addData(@RequestBody @Valid DatiSportivi dati) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //String accessToken = authentication.getCredentials().toString();
        // Utilizza il token di accesso come necessario per l'aggiunta dei dati al database Azure

        //DatiSportivi datiAggiunti = azureDatabaseService.addDataToAzureDatabase(dati);
        azureDatabaseService.addDataToAzureDatabase(dati);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        return new ResponseEntity("Added sucessful!", HttpStatus.OK);
    }


    @GetMapping("/get/id")
    public List<DatiSportivi> getDataObjectsByName(@RequestParam(value= "nome", defaultValue = "0") String nome) {
        return azureDatabaseService.getDatiSportivisBynomeUtente(nome);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestBody byte[] fileData, @RequestParam("fileName") String fileName, @RequestParam("nomeUtente") String utente) {
        try {

            // Recupera il contenitore BLOB
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("lazyhoundcontainer");

            // Crea un nome univoco per il BLOB utilizzando il nome originale del file
            //String blobName = java.util.UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Carica il file nel BLOB
            BlobClient blobClient = containerClient.getBlobClient(utente + "/"+fileName);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
            blobClient.upload(inputStream, fileData.length);

            return "File caricato con successo: ";
        } catch (Exception e) {
            // Gestisci l'errore
            return "Errore durante il caricamento del file: " + e.getMessage();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFolder(@RequestParam("folderName") String folderName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("lazyhoundcontainer");
            String zipFilePath = "/Users/sgranfrix/Download"+ folderName + ".zip"; // Percorso in cui memorizzare il file ZIP
            FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
            ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(fileOutputStream);
            Iterable<BlobItem> blobs = containerClient.listBlobsByHierarchy( folderName + "/");
            for (BlobItem blobItem : blobs) {
                if (!blobItem.isPrefix()) {
                    String fileName = blobItem.getName();

                    BlobClient blobClient = containerClient.getBlobClient(fileName);
                    BinaryData fileContent = blobClient.downloadContent();
                    byte[] fileBytes = ((BinaryData) fileContent).toBytes();

                    ZipArchiveEntry zipEntry = new ZipArchiveEntry(fileName);
                    zipOutputStream.putArchiveEntry(zipEntry);
                    zipOutputStream.write(fileBytes);
                    zipOutputStream.closeArchiveEntry();
                }
            }

            zipOutputStream.finish();
            zipOutputStream.close();

            byte[] zipBytes = Files.readAllBytes(Paths.get(zipFilePath));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", folderName + ".zip");

            System.out.println("KEKU PIUUUUUU");
            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

    }

    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("nomeFile") String nomeFile, String utente) {
        try {


            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("lazyhoundcontainer");
            BlobClient blobClient = containerClient.getBlobClient(utente + "/"+nomeFile);

            BinaryData fileContent = blobClient.downloadContent();
            byte[] bytes = ((BinaryData) fileContent).toBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", nomeFile);
            FileOutputStream outputStream = new FileOutputStream("C:/Users/PC-1-/Desktop/"+nomeFile);
            outputStream.write(bytes);
            outputStream.close();
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
