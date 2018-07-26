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
import org.leanhis.patientmanagement.PatientResource;
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






    }

    private Form buildUpdatePatientForm() {
        PropertyModel<String> inputNameModel = new PropertyModel<>(updatePatientForm, "inputName");
        PropertyModel<String> inputAddressModel = new PropertyModel<>(updatePatientForm, "inputAddress");
        PropertyModel<String> inputDateOfBirthModel = new PropertyModel<>(updatePatientForm, "inputDateOfBirth");
        PropertyModel<String> inputGenderModel = new PropertyModel<>(updatePatientForm, "inputGender");
        List<String> inputGenderOptions = new ArrayList<>();
        inputGenderOptions.add("Male");
        inputGenderOptions.add("Female");
        inputGenderOptions.add("Other");


        Form<Void> addPatientForm = new Form<Void>("addPatientForm") {
            @Override
            protected void onSubmit() {
                PatientResource patient = PatientResource.builder()
                        .name(getAndResetObject(inputNameModel))
                        .address(getAndResetObject(inputAddressModel))
                        .gender(Gender.valueOf(getAndResetObject(inputGenderModel).toUpperCase()))
                        .dateOfBirth(LocalDate.parse(getAndResetObject(inputDateOfBirthModel)))
                        .build();

                patientService.create(patient);
            }
        };

        addPatientForm.add(new TextField("inputName", inputNameModel));
        addPatientForm.add(new TextField("inputAddress", inputAddressModel));
        addPatientForm.add(new TextField("inputDateOfBirth", inputDateOfBirthModel));
        addPatientForm.add(new DropDownChoice<>("inputGender", inputGenderModel, inputGenderOptions));

        return addPatientForm;
    }

    private String getAndResetObject(PropertyModel<String> propertyModel) {
        String object = propertyModel.getObject();
        propertyModel.setObject(null);
        return object;
    }
}
