package com.tiago.money.money.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;
import com.tiago.money.money.config.property.MoneyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Autowired
    private MoneyProperty moneyProperty;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(
                this.moneyProperty.getS3().getClientId(),
                this.moneyProperty.getS3().getClientSecret()
        );

        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                                .standard()
                                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                .build();

        this.criarBucketCasoNaoExista(this.moneyProperty.getS3().getBucketName(), amazonS3);
        this.aplicarRegraDeExpiracaoArquivosS3(amazonS3);
        return amazonS3;
    }


    private void criarBucketCasoNaoExista(String nomeBucket, AmazonS3 amazonS3) {

        if (!amazonS3.doesBucketExistV2(nomeBucket)) {
            amazonS3.createBucket(new CreateBucketRequest(nomeBucket));
        }
    }


    private void aplicarRegraDeExpiracaoArquivosS3(AmazonS3 amazonS3) {

        BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule()
                .withId("Regra de expiração de arquivos temporários")
                .withFilter(new LifecycleFilter(
                        new LifecycleTagPredicate(new Tag("expirar", "true"))
                ))
                .withExpirationInDays(1)
                .withStatus(BucketLifecycleConfiguration.ENABLED);

        BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
                .withRules(rule);

        amazonS3.setBucketLifecycleConfiguration(
                this.moneyProperty.getS3().getBucketName(), configuration);
    }
}
