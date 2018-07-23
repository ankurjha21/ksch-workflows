package org.leanhis.patientmanagement;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    // TODO Unit test for patient creation
    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(PatientEntity.of(patient));
    }

    @Override
    public List<Patient> find(String name) {



        return null;
    }
}
