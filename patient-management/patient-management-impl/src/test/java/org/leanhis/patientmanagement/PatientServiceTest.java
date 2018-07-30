package org.leanhis.patientmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    public void should_calculate_patient_age() {
        Patient patient = buildTestPatient(LocalDate.now().minus(15, YEARS));

        int patientAgeInYears = patientService.getAgeInYears(patient);

        assertEquals("Couldn't calculate patient age correctly.", 15, patientAgeInYears);
    }

    private Patient buildTestPatient(LocalDate dateOfBirth) {
        return Patient.builder()
                .id(UUID.randomUUID())
                .patientNumber("KSA-18-1005")
                .name("John Doe")
                .dateOfBirth(dateOfBirth)
                .gender(Gender.MALE)
                .address("Kirpal Sagar")
                .build();
    }
}
