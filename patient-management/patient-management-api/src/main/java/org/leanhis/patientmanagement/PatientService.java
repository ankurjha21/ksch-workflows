package org.leanhis.patientmanagement;

import java.util.List;

public interface PatientService {

    Patient create(Patient patient);

    List<Patient> findBy(String nameOrId);

    Integer getAgeInYears(Patient patient);
}
