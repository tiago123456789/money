package com.tiago.money.money.storage.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.tiago.money.money.config.property.MoneyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3Service {

    @Autowired
    private MoneyProperty moneyProperty;

    public void criarBucket(String nomeBucket, AmazonS3 amazonS3) {
        if (!amazonS3.doesBucketExistV2(nomeBucket)) {
            amazonS3.createBucket(new CreateBucketRequest(nomeBucket));
        }
    }

    public void aplicarRegraExpiracaoEmObjetosS3(BucketLifecycleConfiguration.Rule regra, AmazonS3 amazonS3) {
        BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
                .withRules(regra);

        amazonS3.setBucketLifecycleConfiguration(
                this.moneyProperty.getS3().getBucketName(), configuration);

    }
}
