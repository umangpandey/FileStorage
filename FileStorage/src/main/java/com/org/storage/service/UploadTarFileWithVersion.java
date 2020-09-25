package com.org.storage.service;

import org.springframework.stereotype.Service;

import com.org.storage.entity.FileStorageData;
import com.org.storage.exception.FileAlreadyExistException;
import com.org.storage.repository.FileRepository;

@Service
public class UploadTarFileWithVersion {
	
	private final FileRepository fileRepository;
	
	public UploadTarFileWithVersion(FileRepository fileRepository) {
		this.fileRepository=fileRepository;
	}
	

	public void uploadTarFile(FileStorageData data)throws FileAlreadyExistException {
		if(validateFileExistance(data.getFileName())) {
			throw new FileAlreadyExistException("File With Same Name Already Existed");
		}		
		fileRepository.save(data);
	}
	
	private Boolean validateFileExistance(String fileName) {
		return fileRepository.findById(fileName).isPresent();
	}
}
