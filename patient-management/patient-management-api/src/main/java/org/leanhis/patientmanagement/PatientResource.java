package org.leanhis.patientmanagement;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
public class PatientResource implements Patient {

    private UUID id;

    private String patientNumber;

    private String name;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String address;
}
