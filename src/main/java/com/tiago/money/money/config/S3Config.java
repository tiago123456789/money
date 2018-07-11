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
import com.tiago.money.money.storage.s3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Autowired
    private MoneyProperty moneyProperty;

    @Autowired
    private S3Service s3Service;

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

        this.s3Service.criarBucket(this.moneyProperty.getS3().getBucketName(), amazonS3);
        this.aplicarRegraDeExpiracaoArquivosS3(amazonS3);
        return amazonS3;
    }

    private void aplicarRegraDeExpiracaoArquivosS3(AmazonS3 amazonS3) {
        BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule()
                .withId("Regra de expiração de arquivos temporários")
                .withFilter(new LifecycleFilter(
                        new LifecycleTagPredicate(new Tag("expirar", "true"))
                ))
                .withExpirationInDays(1)
                .withStatus(BucketLifecycleConfiguration.ENABLED);

        this.s3Service.aplicarRegraExpiracaoEmObjetosS3(rule, amazonS3);
    }
}
