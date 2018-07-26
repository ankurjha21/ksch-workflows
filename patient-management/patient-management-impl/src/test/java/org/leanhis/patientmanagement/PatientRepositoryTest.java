package org.leanhis.patientmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    // TODO Truncate database after each test case

    @Test
    public void should_create_patient() {
        PatientEntity patientEntity = PatientEntity.builder()
                .dateOfBirth(LocalDate.now())
                .gender(Gender.MALE)
                .name("John Doe")
                .build();

        PatientEntity createdPatient = patientRepository.save(patientEntity);

        assertNotNull(createdPatient.getId());
    }

    @Test
    public void should_find_patient_by_name() {
        PatientEntity patient = createTestPatient("KSA-18-1001", "Jane Doe");

        List<PatientEntity> retrievedPatients = patientRepository.findByIdOrName("jane");

        assertEquals("Could not findBy patient in database by searching her name", 1, retrievedPatients.size());
    }

    @Test
    public void should_find_patient_by_id() {
        createTestPatient("KSA-19-1002", "Jane Doe");

        List<PatientEntity> retrievedPatients = patientRepository.findByIdOrName("-19-");

        assertEquals("Could not findBy patient in database by searching her medical record number", 1, retrievedPatients.size());
    }

    private PatientEntity createTestPatient(String patientNumber, String name) {
        return patientRepository.save(PatientEntity.builder()
                .dateOfBirth(LocalDate.now())
                .gender(Gender.FEMALE)
                .name(name)
                .patientNumber(patientNumber)
                .build());
    }
}
