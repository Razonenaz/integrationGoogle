package com.integration.googleSheets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
}
