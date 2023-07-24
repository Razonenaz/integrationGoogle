package com.integration.googleSheets.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
}
