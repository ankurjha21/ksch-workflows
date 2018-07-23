package org.leanhis.patientmanagement;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends CrudRepository<PatientEntity, UUID> {

    List<PatientEntity> findByNameIgnoreCaseContainingOrMedicalRecordNumber(String name, String medicalRecordNumber);
}
