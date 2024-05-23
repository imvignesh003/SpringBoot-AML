package com.example.aml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ArtMediaLiteratureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtMediaLiteratureApplication.class, args);
	}

}

/*
For API instructions, see Controllers under "api" package

PROJECT STRUCTURE

REQUEST -> API (Controller) LAYER -> SERVICE LAYER -> DATA ACCESS LAYER -> DB
 */

/*
MODEL STRUCTURE

BOOK
- ID
- NAME
- AUTHOR(s)
- WORD COUNT
- YEAR OF PUBLICATION
 */
