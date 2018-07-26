package org.leanhis.patientmanagement;

import lombok.SneakyThrows;
import org.junit.Test;

import javax.management.RuntimeMBeanException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
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
        Arrays.stream(Patient.class.getDeclaredMethods()).filter(m -> m.getName().startsWith("get") || m.getName().startsWith("is")).forEach(m -> {
            try {
                Object valuePatient = m.invoke(patient);
                Object valuePatientEnity = m.invoke(patientEntity);

                assertEquals(m.getName() + "() was not initialized in the PatientEntity", valuePatient, valuePatientEnity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Patient buildTestPatient() {
        return new Patient() {
            @Override
            public UUID getId() {
                return null;
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
                return LocalDate.now();
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
    }
}


