package Configurations;


import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.CosmosFactory;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.core.convert.MappingCosmosConverter;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import reactor.util.annotation.Nullable;

@Configuration
@EnableCosmosRepositories
@PropertySource("classpath:application.properties")
public class CosmosDBConfiguration extends AbstractCosmosConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosDBConfiguration.class);


    @Value("${spring.cloud.azure.cosmos.uri}")
    private String uri;

    @Value("${spring.cloud.azure.cosmos.key}")
    private String key;

    @Value("${azure.cosmos.secondaryKey}")
    private String secondaryKey;

    @Value("${spring.cloud.azure.cosmos.database}")
    private String dbName;

    @Value("${azure.cosmos.queryMetricsEnabled}")
    private boolean queryMetricsEnabled;

    @Value("${azure.cosmos.maxDegreeOfParallelism}")
    private int maxDegreeOfParallelism;

    @Value("${azure.cosmos.maxBufferedItemCount}")
    private int maxBufferedItemCount;

    @Value("${azure.cosmos.responseContinuationTokenLimitInKb}")
    private int responseContinuationTokenLimitInKb;

    private AzureKeyCredential azureKeyCredential;

    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        this.azureKeyCredential = new AzureKeyCredential(key);
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        return new CosmosClientBuilder()
                .endpoint(uri)
                .credential(azureKeyCredential)
                .directMode(directConnectionConfig, gatewayConnectionConfig);
    }





    /*@Override
     public CosmosConfig cosmosConfig() {
         return CosmosConfig.builder()
                 .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
                 .build();
     }*/
    @Override
    public CosmosConfig cosmosConfig() {
        return CosmosConfig.builder()
                .enableQueryMetrics(queryMetricsEnabled)
                .maxDegreeOfParallelism(maxDegreeOfParallelism)
                .maxBufferedItemCount(maxBufferedItemCount)
                .responseContinuationTokenLimitInKb(responseContinuationTokenLimitInKb)
                .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
                .build();
    }
    public void switchToSecondaryKey() {
        this.azureKeyCredential.update(secondaryKey);
    }

    @Override
    protected String getDatabaseName() {
        return "LazyHound";
    }



    private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

        @Override
        public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
            LOGGER.info("Response Diagnostics {}", responseDiagnostics);
        }
    }

}
