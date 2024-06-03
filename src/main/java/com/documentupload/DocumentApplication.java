package com.documentupload;
import jakarta.annotation.Resource;
import com.documentupload.Service.DocumentStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentApplication implements CommandLineRunner {
@Resource
	DocumentStorageService documentStorageService;

	public static void main(String[] args) {
		SpringApplication.run(DocumentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		documentStorageService.init();
	}
}
