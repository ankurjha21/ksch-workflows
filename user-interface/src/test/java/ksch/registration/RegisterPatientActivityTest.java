package ksch.registration;

import ksch.WebPageTest;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterPatientActivityTest extends WebPageTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void should_render_patient_search_result_list() {
        createDummyPatients();
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.setValue("patientSearchTerm", "doe");
        formTester.submit();

        System.out.println(tester.getLastResponseAsString());
        assertTrue("Table with patients wasn't rendered.",
                tester.getLastResponseAsString().contains("<th scope=\"col\">ID</th>"));
    }

    @Test
    public void should_open_patient_details() {
        createDummyPatients();
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.setValue("patientSearchTerm", "doe");
        formTester.submit();

        assertTrue(tester.getLastResponseAsString().contains("/edit-patient?id="));
    }

    @Test
    public void should_resubmit_patient_search() {
        createDummyPatients();
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);
        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.setValue("patientSearchTerm", "doe");
        formTester.submit();

        formTester = tester.newFormTester("patientSearchForm", false);
        formTester.setValue("patientSearchTerm", "doe");
        formTester.submit();

        assertTrue("Table with patients wasn't rendered.",
                tester.getLastResponseAsString().contains("<th scope=\"col\">ID</th>"));
    }

    @Test
    public void should_render_message_about_no_search_results() {
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.submit();

        System.out.println(tester.getLastResponseAsString());
        assertTrue("Notification about missing search results wasn't rendered.",
                tester.getLastResponseAsString().contains("Cannot find patient with given ID or name"));
        assertFalse("Table with patients was rendered even though there are no patients in the search results",
                tester.getLastResponseAsString().contains(
                        "<th scope=\"col\" style=\"width:250px;\">Medical Record Number</th>"));
    }

    @Test
    public void should_add_new_patient() {
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("addPatientForm", false);
        formTester.setValue("inputName", "Fritz");
        formTester.select("inputGender", 0);
        formTester.setValue("inputDateOfBirth", "2000-07-27");
        formTester.setValue("inputAddress", "Kirpal Sagar");
        formTester.submit();

        List<Patient> searchResults = patientService.findBy("Fritz");
        assertEquals("Could not create new patient",
                1, searchResults.size());
        Patient retrievedPatient = searchResults.get(0);
        assertEquals("MALE", retrievedPatient.getGender().toString());
        assertEquals("Kirpal Sagar", retrievedPatient.getAddress());
        assertEquals(LocalDate.of(2000, 7, 27), retrievedPatient.getDateOfBirth());
    }

    private void createDummyPatients() {
        Patient patient1 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public void setId(UUID id) {

            }

            @Override
            public String getPatientNumber() {
                return "KSA-18-1001";
            }

            @Override
            public void setPatientNumber(String patientNumber) {

            }

            @Override
            public String getName() {
                return "John Doe";
            }

            @Override
            public void setName(String name) {

            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(25, YEARS);
            }

            @Override
            public void setDateOfBirth(LocalDate dateOfBirth) {

            }

            @Override
            public Gender getGender() {
                return Gender.MALE;
            }

            @Override
            public void setGender(Gender gender) {

            }

            @Override
            public String getAddress() {
                return "Kirpal Sagar";
            }

            @Override
            public void setAddress(String address) {

            }
        };

        Patient patient2 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public void setId(UUID id) {

            }

            @Override
            public String getPatientNumber() {
                return "KSA-18-1002";
            }

            @Override
            public void setPatientNumber(String patientNumber) {

            }

            @Override
            public String getName() {
                return "Jane Doe";
            }

            @Override
            public void setName(String name) {

            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(20, YEARS);
            }

            @Override
            public void setDateOfBirth(LocalDate dateOfBirth) {

            }

            @Override
            public Gender getGender() {
                return Gender.FEMALE;
            }

            @Override
            public void setGender(Gender gender) {

            }

            @Override
            public String getAddress() {
                return "Kirpal Sagar";
            }

            @Override
            public void setAddress(String address) {

            }
        };

        patientService.create(patient1);
        patientService.create(patient2);
    }
}
