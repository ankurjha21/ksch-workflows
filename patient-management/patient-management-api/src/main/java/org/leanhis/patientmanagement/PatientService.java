package org.leanhis.patientmanagement;

import java.util.List;

public interface PatientService {

    Patient create(Patient patient);

    List<Patient> find(String nameOrId);
}
