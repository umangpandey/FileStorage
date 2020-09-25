package com.org.storage.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.storage.entity.FileStorageData;

@Repository
public interface FileRepository extends JpaRepository<FileStorageData, String>{
	
	@Query("select fileName from FileStorageData")
	public List<String> findAllByString();
}