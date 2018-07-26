package ksch.registration;

import ksch.WebPageTest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.Assert.assertEquals;

public class EditPatientDetailsActivityTest extends WebPageTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void should_open_patient_details() {
        login("user", "pwd");
        Patient patient = createDummyPatient();
        PageParameters parameters = new PageParameters();
        parameters.add("id", patient.getId());

        tester.startPage(EditPatientDetailsActivity.class, parameters);

        tester.assertRenderedPage(EditPatientDetailsActivity.class);
        tester.assertContains(patient.getName());
    }

    @Test
    public void should_update_patient_details() {
        login("user", "pwd");
        Patient patient = createDummyPatient();
        PageParameters parameters = new PageParameters();
        parameters.add("id", patient.getId());
        tester.startPage(EditPatientDetailsActivity.class, parameters);

        FormTester formTester = tester.newFormTester("updatePatientForm", false);
        formTester.setValue("inputAddress", "St. Gilgen");
        formTester.submit();

        Patient updatedPatient = patientService.getById(patient.getId());
        assertEquals("St. Gilgen", updatedPatient.getAddress());
    }

    private Patient createDummyPatient() {
        Patient patient = new Patient() {
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

        return patientService.create(patient);
    }
}
