package org.leanhis.patientmanagement;

import java.time.LocalDate;
import java.util.UUID;

public interface Patient {

    UUID getId();

    void setId(UUID id);

    String getPatientNumber();

    void setPatientNumber(String patientNumber);

    String getName();

    void setName(String name);

    LocalDate getDateOfBirth();

    void setDateOfBirth(LocalDate dateOfBirth);

    Gender getGender();

    void setGender(Gender gender);

    String getAddress();

    void setAddress(String address);
}
