package ksch.registration;

import ksch.ApplicationFrame;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MountPath("/registration/edit-patient/{id}")
@AuthorizeInstantiation({"NURSE", "CLERK"})
public class EditPatientDetailsActivity extends ApplicationFrame {

    @SpringBean
    private PatientService patientService;

    private PatientFormProperties updatePatientForm = new PatientFormProperties();

    public EditPatientDetailsActivity(PageParameters pageParameters) {
        super(pageParameters);

        UUID patientID = UUID.fromString(pageParameters.get("id").toString());

        Patient patient = patientService.getById(patientID);

        add(buildUpdatePatientForm(patient));
    }

    private Form buildUpdatePatientForm(Patient patient) {
        PropertyModel<String> inputNameModel = new PropertyModel<>(updatePatientForm, "inputName");
        PropertyModel<String> inputAddressModel = new PropertyModel<>(updatePatientForm, "inputAddress");
        PropertyModel<String> inputDateOfBirthModel = new PropertyModel<>(updatePatientForm, "inputDateOfBirth");
        PropertyModel<String> inputGenderModel = new PropertyModel<>(updatePatientForm, "inputGender");
        List<String> inputGenderOptions = new ArrayList<>();
        inputGenderOptions.add("MALE");
        inputGenderOptions.add("FEMALE");
        inputGenderOptions.add("OTHER");

        updatePatientForm.setInputName(patient.getName());
        updatePatientForm.setInputGender(patient.getGender().toString());
        updatePatientForm.setInputDateOfBirth(patient.getDateOfBirth().toString());
        updatePatientForm.setInputAddress(patient.getAddress());

        Form<Void> form = new Form<Void>("updatePatientForm") {
            @Override
            protected void onSubmit() {
                patient.setName(inputNameModel.getObject());
                patient.setAddress(inputAddressModel.getObject());
                patient.setGender(Gender.valueOf(inputGenderModel.getObject()));
                patient.setDateOfBirth(LocalDate.parse(inputDateOfBirthModel.getObject()));

                patientService.update(patient);
            }
        };

        form.add(new TextField("inputName", inputNameModel));
        form.add(new TextField("inputAddress", inputAddressModel));
        form.add(new TextField("inputDateOfBirth", inputDateOfBirthModel));
        form.add(new DropDownChoice<>("inputGender", inputGenderModel, inputGenderOptions));

        return form;
    }
}
