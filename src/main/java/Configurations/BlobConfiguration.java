package Configurations;


import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobConfiguration {

    //@Value("${azure.storage.account-name}")
    private String accountName="risorseblob";

    //@Value("${azure.storage.account-key}")
    private String accountKey = "+6lMC2moAKq+xZI5/XN9WUm8Rx4Rm/EzAWYu3vMRSzbT/sE0t79CENOQ5Vx/A8DVZUIqiBkulLnp+AStocjv0Q==";

    //@Value("${azure.storage.blob-endpoint}")
    private String blobEndpoint = "https://risorseblob.blob.core.windows.net/";


    @Bean
    public BlobServiceClient blobServiceClient() {
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
        return new BlobServiceClientBuilder()
                .endpoint(blobEndpoint)
                .credential(credential)
                .buildClient();
    }
}