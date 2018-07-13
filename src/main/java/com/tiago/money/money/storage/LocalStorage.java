package com.tiago.money.money.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Qualifier(value = "local")
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

	public void delete(String file) {
		String pathFile = this.pathDirStorageDefault + file;
		File deletadorFile = new File(pathFile);
		deletadorFile.delete();
	}

	@Override
	public void maintainFile(String file) {
		throw new NotImplementedException("Método não implementado");
	}

}
