package org.leanhis.patientmanagement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.YEARS;

@Service
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
    public List<Patient> findBy(String nameOrMedicalRecordNumber) {
        return patientRepository.findByNameOrMedicalRecordNumber(nameOrMedicalRecordNumber).stream()
                .map(p -> (Patient) p)
                .collect(Collectors.toList());
    }

    @Override
    public int getAgeInYears(Patient patient) {
        return (int) patient.getDateOfBirth().until(now(), YEARS);
    }
}
