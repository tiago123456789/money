package com.tiago.money.money.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tiago.money.money.config.property.MoneyProperty;
import com.tiago.money.money.storage.s3.factory.S3Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
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

	private String gerarNomeArquivo(String filename) {
		return UUID.randomUUID().toString() + "_" + filename;
	}

}
