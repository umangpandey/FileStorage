package com.org.storage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.storage.repository.FileRepository;

@Service
public class ListFileNames {

	private final FileRepository fileRepository;
	
	public ListFileNames(FileRepository fileRepository) {
		this.fileRepository=fileRepository;
	}
	
	public List<String> getListOfFile(){
		return fileRepository.findAllByString();
	}
	
}
