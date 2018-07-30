package org.leanhis.patientmanagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class Patient {

    private UUID id;

    private String patientNumber;

    private String name;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String address;
}
