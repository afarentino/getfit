package com.tnctech.getfit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.ApplicationRunner;

import java.util.List;

/**
 * To setup a Spring Boot app as a console app
 * @see: https://github.com/attacomsian/code-examples/tree/master/spring-boot/console-app
 *
 * Goal Read a text file from the command line.
 */
@SpringBootApplication
public class GetfitApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(GetfitApplication.class);

	private TextFileService fileService;

	private void setFileService(TextFileService fileService) {
		this.fileService = fileService;
	}

	private static String getFileName(ApplicationArguments args) {
		if ( args.containsOption("inFile") ) {
			List<String> values = args.getOptionValues("inFile");
			if (values.size() > 1) {
				throw new IllegalStateException("Only one inFile supported at this time");
			}
			return values.get(0);
		}
		throw new IllegalArgumentException("Error input file required: Specify a file to use using an --inFile= argument");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String fileName = getFileName(args);
        // Construct our TextFileService
		this.fileService = new TextFileService(fileName);
		// Do we need all or can we just grab nextRecord and from there do a dump?
		fileService.getNextRecord();
		// Append to CSV file

		logger.info("Custom Application Runner complete");
	}

	// Runs last
	public static void main(String[] args) {
		logger.info("Before Spring App run");
		SpringApplication.run(GetfitApplication.class, args);
	}

}
