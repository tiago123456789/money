package com.tiago.money.money.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {
	
	public void store(MultipartFile file);
}
