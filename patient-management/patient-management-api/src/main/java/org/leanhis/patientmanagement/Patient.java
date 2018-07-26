package org.leanhis.patientmanagement;

import java.time.LocalDate;
import java.util.UUID;

public interface Patient {

    // TODO It seems like this is only required for the Patient entity
    UUID getId();

    String getPatientNumber();

    String getName();

    LocalDate getDateOfBirth();

    Gender getGender();

    String getAddress();
}
