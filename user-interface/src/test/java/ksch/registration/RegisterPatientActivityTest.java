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
                tester.getLastResponseAsString().contains("<th scope=\"col\" style=\"width:250px;\">Medical Record Number</th>"));
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

        System.out.println(tester.getLastResponseAsString());
        assertTrue("Table with patients wasn't rendered.",
                tester.getLastResponseAsString().contains("<th scope=\"col\" style=\"width:250px;\">Medical Record Number</th>"));
    }

    @Test
    public void should_render_message_about_no_search_results() {
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.submit();

        System.out.println(tester.getLastResponseAsString());
        assertTrue("Notification about missing search results wasn't rendered.",
                tester.getLastResponseAsString().contains("Cannot find patient with given Medical Record Number or name"));
        assertFalse("Table with patients was rendered even though there are no patients in the search results",
                tester.getLastResponseAsString().contains("<th scope=\"col\" style=\"width:250px;\">Medical Record Number</th>"));
    }

    @Test
    public void should_add_new_patient() {
        login("user", "pwd");
        tester.startPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("addPatientForm", false);
        formTester.setValue("inputName", "Fritz");
        formTester.select("inputGender", 0);
        formTester.submit();

        List<Patient> searchResults = patientService.findBy("Fritz");
        assertEquals("Could not create new patient",
                1, searchResults.size());
        assertEquals("MALE", searchResults.get(0).getGender().toString());
    }

    private void createDummyPatients() {
        Patient patient1 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getMedicalRecordNumber() {
                return "KSA-18-1001";
            }

            @Override
            public String getName() {
                return "John Doe";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(25, YEARS);
            }

            @Override
            public Gender getGender() {
                return Gender.MALE;
            }
        };

        Patient patient2 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getMedicalRecordNumber() {
                return "KSA-18-1002";
            }

            @Override
            public String getName() {
                return "Jane Doe";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(20, YEARS);
            }

            @Override
            public Gender getGender() {
                return Gender.FEMALE;
            }
        };

        patientService.create(patient1);
        patientService.create(patient2);
    }
}
