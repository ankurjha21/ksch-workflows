package org.leanhis.patientmanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface PatientRepository extends CrudRepository<PatientEntity, UUID> {

    @Query("Select p from PatientEntity p where " +
            "lower(p.name) like lower(concat('%',:nameOrMedicalRecordNumber,'%')) or " +
            "lower(p.medicalRecordNumber) like lower(concat('%',:nameOrMedicalRecordNumber,'%'))" )
    List<PatientEntity> findByNameOrMedicalRecordNumber(@Param("nameOrMedicalRecordNumber") String nameOrMedicalRecordNumber);
}
