package com.tiago.money.money.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalStorage implements Storage {
	
	private String pathDirStorageDefault = "/home/tiago/Documentos";
	
	@Override
	public void store(MultipartFile file) {
		try {
			String pathFile = this.pathDirStorageDefault + file.getOriginalFilename();
			OutputStream writer = new FileOutputStream(pathFile);
			writer.write(file.getBytes());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
