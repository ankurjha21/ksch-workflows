package org.leanhis.patientmanagement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
        return patientRepository.findByIdOrName(nameOrMedicalRecordNumber).stream()
                .map(p -> (Patient) p)
                .collect(Collectors.toList());
    }

    @Override
    public Patient getById(UUID patientId) {
        return patientRepository.getById(patientId);
    }

    @Override
    public Integer getAgeInYears(Patient patient) {
        if (patient.getDateOfBirth() == null) {
            return null;
        }

        return (int) patient.getDateOfBirth().until(now(), YEARS);
    }

    @Override
    public void update(Patient patient) {
        patientRepository.save(PatientEntity.of(patient));
    }
}
