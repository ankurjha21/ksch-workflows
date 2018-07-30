package ksch.registration;

import lombok.Setter;

import java.io.Serializable;

@Setter
public class PatientFormProperties implements Serializable {

    private String inputName;

    private String inputGender;

    private String inputDateOfBirth;

    private String inputAddress;
}
