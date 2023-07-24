package com.integration.googleSheets.repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.integration.googleSheets.model.Student;

@Repository
public class StudentRepository {
	private static String applicationName;
	private static Credential authorized;
	private static Sheets sheetsService;
	private static String spreadsheetId;

	public StudentRepository() throws IOException, GeneralSecurityException {
		authorized = DBContext.authorize();
		spreadsheetId = "1zIPhCaePEWaOSOgA-M1v3GzuEyaEQSoXIO85ij77X0Q";
		applicationName = "Students";
		sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), authorized).setApplicationName(applicationName).build();
	}

	public List<List<Object>> getAllStudent() throws IOException {

		return sheetsService.spreadsheets().values().get(spreadsheetId, "A:C").execute().getValues();
	}

	public List<List<Object>> getStudentById(int id) throws IOException {

		return sheetsService.spreadsheets().values().get(spreadsheetId, String.format("A%d:C%d", id, id)).execute()
				.getValues();
	}

	public Student addStudent(Student student) throws IOException {
		ValueRange appendBody = new ValueRange().setValues(
				Arrays.asList(Arrays.asList(student.getFirstName(), student.getMiddleName(), student.getLastName())));
		sheetsService.spreadsheets().values().append(spreadsheetId, "A:C", appendBody)
				.setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true)
				.execute();

		return student;
	}

	public Student updateStudentById(int id, Student student) throws IOException {
		ValueRange updateBody = new ValueRange().setValues(
				Arrays.asList(Arrays.asList(student.getFirstName(), student.getMiddleName(), student.getLastName())));
		sheetsService.spreadsheets().values().update(spreadsheetId, String.format("A%d:C%d", id, id), updateBody)
				.setValueInputOption("RAW").setIncludeValuesInResponse(true).execute();

		return student;
	}

	public String deleteStudentById(int id) throws IOException {
		DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
				.setRange(new DimensionRange().setSheetId(0).setDimension("ROWS").setStartIndex(id));
		List<Request> requests = new ArrayList<>();
		requests.add(new Request().setDeleteDimension(deleteRequest));
		BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		sheetsService.spreadsheets().batchUpdate(spreadsheetId, body).execute();

		return "Deleted";
	}
}
