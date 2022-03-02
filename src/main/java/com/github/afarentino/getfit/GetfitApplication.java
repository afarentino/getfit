package com.github.afarentino.getfit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.ApplicationRunner;

import java.io.File;
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
		logger.error("Input file required: Specify a file to use using an --inFile= argument");
		return null;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String fileName = getFileName(args);
        if (fileName == null) {
			return;
		}
		// Construct our TextFileService
		this.fileService = new TextFileService(fileName);
		// Convert to a CSV file
		File csv = fileService.convertToCSV();
		if (csv != null && csv.exists()) {
			logger.info("CSV generated is " + csv.getName());
		} else {
			logger.error("Failed to generated CSV");
		}
	}

	// Runs last
	public static void main(String[] args) {
		logger.info("Before Spring App run");
		Thread.currentThread().setUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				logger.error(t + "throws exception: " + e);
			}
		});
		SpringApplication.run(GetfitApplication.class, args);
	}

}
