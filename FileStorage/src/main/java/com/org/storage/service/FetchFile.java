package com.org.storage.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.org.storage.entity.FileStorageData;
import com.org.storage.exception.FileNotFoundException;
import com.org.storage.repository.FileRepository;

@Service
public class FetchFile {

	private final FileRepository fileRepository;
	
	public FetchFile(FileRepository fileRepository) {
		this.fileRepository=fileRepository;
	}
	
	public FileStorageData fetchFileFromRepo(String fileName) throws FileNotFoundException {
		
		Optional<FileStorageData> data = fileRepository.findById(fileName);
		if(!data.isPresent()) {
			throw new FileNotFoundException("File is not available in store");
		}
		return data.get();
	}
}
