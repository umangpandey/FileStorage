package com.org.storage.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.net.MediaType;

import com.org.storage.service.FetchFile;
import com.org.storage.service.ListFileNames;
import com.org.storage.service.UploadTarFileWithVersion;


@RunWith(SpringRunner.class)
@WebMvcTest({FileStorageApiController.class})
public class FileStorageApiControllerTest {
	
	@Autowired
	private MockMvc mocMvc;
	
	@MockBean
	private UploadTarFileWithVersion uploadTarFileWithVersion;
	@MockBean
	private ListFileNames listFileNames;
	@MockBean
	private FetchFile fetchFile;
		
	private MockMultipartFile tarFile;
	
	@Before
	public void init() {
		tarFile = new MockMultipartFile("tar_file","DummyFile.tar.gz",MediaType.OCTET_STREAM.toString(),new byte[] {56});		
	}
	
	/*
	 * positive testcase for correct file upload
	 */
	@Test
	public void shouldReturnUploadedTarFile()throws Exception{		
		
		mocMvc.perform(multipart("/api/v1/import")
				.file(tarFile))
				.andExpect(status().isOk());				
		
	}
	
	/*
	 * Negative Testcase for wrong extension
	 */
	@Test
	public void shouldReturnBadRequestDueToWrongExtension() throws Exception{
		MockMultipartFile file =new MockMultipartFile("tar_file","DummyFile.pdf",MediaType.OCTET_STREAM.toString(),new byte[] {56});
		mocMvc.perform(multipart("/api/v1/import")
				.file(file))
				.andExpect(status().isBadRequest());	
	}

}
