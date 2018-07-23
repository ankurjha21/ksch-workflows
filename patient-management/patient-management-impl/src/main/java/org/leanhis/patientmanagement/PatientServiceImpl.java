package org.leanhis.patientmanagement;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    // TODO Unit test for patient creation
    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(PatientEntity.of(patient));
    }

    // TODO Unit test for patient search by name or medical record number
    @Override
    public List<Patient> find(String nameOrMedicalRecordNumber) {
        return patientRepository.findByNameOrMedicalRecordNumber(nameOrMedicalRecordNumber).stream()
                .map(p -> (Patient) p)
                .collect(Collectors.toList());
    }
}
