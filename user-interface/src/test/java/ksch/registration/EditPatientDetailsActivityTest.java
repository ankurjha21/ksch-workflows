package ksch.registration;

import ksch.WebPageTest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.YEARS;

public class EditPatientDetailsActivityTest extends WebPageTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void should_open_patient_details() {
        login("user", "pwd");
        UUID patientId = createDummyPatient();
        PageParameters parameters = new PageParameters();
        parameters.add("id", patientId);

        tester.startPage(EditPatientDetailsActivity.class, parameters);

        System.out.println(tester.getLastRenderedPage().toString());
        tester.assertRenderedPage(EditPatientDetailsActivity.class);
    }

    @Test
    public void should_update_patient_details() {

    }

    private UUID createDummyPatient() {
        Patient patient = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getPatientNumber() {
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

            @Override
            public String getAddress() {
                return "Kirpal Sagar";
            }
        };

        patient = patientService.create(patient);

        return patient.getId();
    }
}
