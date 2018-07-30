package org.leanhis.patientmanagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "patient")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class PatientEntity implements Patient, Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    // TODO Move ID generator in package info file
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
        )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "patientnumber")
    private String patientNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "address")
    private String address;

    public static PatientEntity of(Patient patient) {
        return PatientEntity.builder()
                .id(patient.getId())
                .patientNumber(patient.getPatientNumber())
                .name(patient.getName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .build();
    }
}
