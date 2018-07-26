package org.leanhis.patientmanagement;

import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PatientEntityTest {

    @Test
    public void should_build_entity_for_patient() {
        Patient patient = buildTestPatient();

        PatientEntity patientEntity = PatientEntity.of(patient);

        assertAllAttributesEqual(patient, patientEntity);
    }

    @SneakyThrows
    private void assertAllAttributesEqual(Patient patient, PatientEntity patientEntity) {
        for (Method m : Patient.class.getDeclaredMethods()) {
            Object valuePatient = m.invoke(patient);
            Object valuePatientEnity = m.invoke(patientEntity);

            assertEquals(m.getName() + "() was not initialized in the PatientEntity", valuePatient, valuePatientEnity);
        }
    }

    private Patient buildTestPatient() {
        return new Patient() {
            @Override
            public UUID getId() {
                return null;
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
                return LocalDate.now();
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
    }
}


