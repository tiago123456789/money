package com.tiago.money.money.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.tiago.money.money.config.property.MoneyProperty;
import com.tiago.money.money.storage.s3.factory.S3Factory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@Qualifier(value = "s3")
public class S3Storage implements Storage {

	@Autowired
	private MoneyProperty moneyProperty;

	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public void store(MultipartFile file) {
		try {
			PutObjectRequest putObjectRequest = S3Factory.getPutObjectRequest(
					this.moneyProperty.getS3().getBucketName(),
					this.gerarNomeArquivo(file.getOriginalFilename()),
					file
			);

			putObjectRequest
					.setTagging(S3Factory.getObjectTag("expirar", "true"));

			this.amazonS3.putObject(putObjectRequest);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível armazenar arquivo no s3.");
		}
	}

	public void delete(String file) {
		DeleteObjectRequest deleteObjectRequest = S3Factory
				.getDeleteObjectRequest(this.moneyProperty.getS3().getBucketName(), file);

		this.amazonS3.deleteObject(deleteObjectRequest);
	}

	public void maintainFile(String object) {
		SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(
				this.moneyProperty.getS3().getBucketName(),
				object,
				new ObjectTagging(Collections.emptyList())
		);

		this.amazonS3.setObjectTagging(setObjectTaggingRequest);
	}

	private String gerarNomeArquivo(String filename) {
		return UUID.randomUUID().toString() + "_" + filename;
	}

}
