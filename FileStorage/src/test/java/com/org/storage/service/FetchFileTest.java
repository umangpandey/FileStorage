package com.org.storage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.org.storage.entity.FileStorageData;
import com.org.storage.repository.FileRepository;

@RunWith(SpringRunner.class)
public class FetchFileTest {
	
	@MockBean
	private FileRepository fileRepository;
	
	private FetchFile fetchFile;
	
	@Before
	public void init() {
			        fetchFile= new FetchFile(fileRepository);
	}
	
	
	@Test
	public void shouldReturnValidData()throws Exception{
		FileStorageData data = new FileStorageData();
		data.setFile(new byte[] {23});
		data.setFileName("Dummy.tar.gz");
        doReturn(Optional.of(data)).when(fileRepository).findById("Dummy.tar.gz")	;
		FileStorageData observed= fetchFile.fetchFileFromRepo("Dummy.tar.gz");
		assertThat(observed.getFileName()).isEqualTo("Dummy.tar.gz");
		
	}

}
