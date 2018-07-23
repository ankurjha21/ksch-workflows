package org.leanhis.patientmanagement;

import java.time.LocalDate;
import java.util.UUID;

public interface Patient {

    UUID getId();

    /**
     * @see <a href="https://ushik.ahrq.gov/ViewItemDetails?system=ps&itemKey=88720000">ushik.ahrq.gov</a>
     */
    String getMedicalRecordNumber();

    String getName();

    LocalDate getDateOfBirth();

    Gender getGender();
}
