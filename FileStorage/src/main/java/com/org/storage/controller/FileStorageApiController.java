package com.org.storage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.org.storage.entity.FileStorageData;
import com.org.storage.exception.FileAlreadyExistException;
import com.org.storage.exception.FileNotFoundException;
import com.org.storage.exception.InvalidFileExtension;
import com.org.storage.service.FetchFile;
import com.org.storage.service.ListFileNames;
import com.org.storage.service.UploadTarFileWithVersion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Guidelines", description = "Guidelines for Api contracts used in microservice")
public class FileStorageApiController {
	
	private final UploadTarFileWithVersion uploadTarFileWithVersion;
	private final ListFileNames listFileNames;
	private final FetchFile fetchFile;
	
	public FileStorageApiController(UploadTarFileWithVersion uploadTarFileWithVersion,ListFileNames listFileNames,FetchFile fetchFile) {
		this.uploadTarFileWithVersion=uploadTarFileWithVersion;
		this.listFileNames=listFileNames;
		this.fetchFile=fetchFile;
	}
	
	@PostMapping("/import")
	@ApiOperation(value = "Make a POST request to upload the file",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadTarFile(
			 @ApiParam(name = "tar_file", value = "Select to browse", required = true)
			@RequestParam(value="tar_file",required=true) MultipartFile tarFile
			) throws IOException, FileAlreadyExistException, InvalidFileExtension{
		FileStorageData fileObj = new FileStorageData();		
		fileObj.setFileName(tarFile.getResource().getFilename());
		String[] extension = tarFile.getResource().getFilename().split("\\.");
		if(!"tar".equals(extension[1])) {
			throw new InvalidFileExtension("Uploaded file is not .tar");
		}
		fileObj.setFile(tarFile.getBytes());	
		uploadTarFileWithVersion.uploadTarFile(fileObj);
		return ResponseEntity.ok().body("File saved successfully :"+tarFile.getResource().getFilename());
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "Make a get request to fetch the file list",
    produces = "text/plain")
	public ResponseEntity<List<String>> listFileNames(){		
		return ResponseEntity.ok().body(listFileNames.getListOfFile());
	}
	
	@GetMapping("/pull/{fileName}")
	@ApiOperation(value = "Make a get request to fetch the file with file extension",
    produces = "application/x-tar")
	public ResponseEntity<byte[]> fetchFileByName(
			@PathVariable(value="fileName",required=true) String fileName 
			) throws FileNotFoundException{
		FileStorageData fileData = fetchFile.fetchFileFromRepo(fileName);
		HttpHeaders headers = new HttpHeaders(); 
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +fileData.getFileName());
		headers.add(HttpHeaders.CONTENT_TYPE,"application/x-tar");
		return ResponseEntity.ok().
		headers(headers).
		body(fileData.getFile());		
	}
	
	

}
