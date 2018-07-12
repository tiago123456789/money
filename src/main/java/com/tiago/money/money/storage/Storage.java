package com.tiago.money.money.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {
	
	void store(MultipartFile file);
	void maintainFile(String file);
}
