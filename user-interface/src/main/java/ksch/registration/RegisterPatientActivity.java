package ksch.registration;

import ksch.ApplicationFrame;
import lombok.extern.java.Log;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.PatientResource;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;

@MountPath("/registration/register-patient")
@AuthorizeInstantiation({"NURSE", "CLERK"})
@Log
public class RegisterPatientActivity extends ApplicationFrame {

    @SpringBean
    private PatientService patientService;

    private String patientSearchTerm = null;

    private String inputName = null;

    private String inputGender = null;

    public RegisterPatientActivity(PageParameters pageParameters) {
        super(pageParameters);

        WebMarkupContainer patientListContainer = new WebMarkupContainer("patientList");
        WebMarkupContainer noSearchResultsMessageContainer = new WebMarkupContainer("noSearchResultsMessage");

        add(patientListContainer);
        add(noSearchResultsMessageContainer);

        patientListContainer.setVisible(false);
        noSearchResultsMessageContainer.setVisible(false);

        PropertyModel<String> patientSearchTermModel = new PropertyModel<>(this, "patientSearchTerm");

        Form<?> patientSearchForm = new Form<Void>("patientSearchForm") {

            @Override
            protected void onSubmit() {
                String patientSearchTerm = patientSearchTermModel.getObject();

                List<Patient> matchingPatients = patientService.findBy(patientSearchTerm);


                if (matchingPatients.size() > 0) {
                    ListView lv = new ListView<Patient>("patients", matchingPatients) {
                        @Override
                        protected void populateItem(ListItem<Patient> item) {
                            Patient patient = item.getModelObject();

                            item.add(new Label("medicalRecordNumber", patient.getMedicalRecordNumber()));
                            item.add(new Label("name", patient.getName()));
                            item.add(new Label("gender", "not implemented yet"));
                            item.add(new Label("age", 15));
                        }
                    };

                    patientListContainer.removeAll(); // required to be able to re-submit search
                    patientListContainer.add(lv);
                    patientListContainer.setVisible(true);
                } else {
                    noSearchResultsMessageContainer.setVisible(true);
                }

                patientSearchTermModel.setObject(null);
            }
        };

        patientSearchForm.add(new TextField("patientSearchTerm", patientSearchTermModel));
        add(patientSearchForm);

        add(buildCreatePatientForm());
    }

    private Form buildCreatePatientForm() {
        PropertyModel<String> inputNameModel = new PropertyModel<>(this, "inputName");
        PropertyModel<String> inputGenderModel = new PropertyModel<>(this, "inputGender");
        List<String> inputGenderOptions = new ArrayList<>();
        inputGenderOptions.add("Male");
        inputGenderOptions.add("Female");
        inputGenderOptions.add("Other");


        Form<Void> addPatientForm = new Form<Void>("addPatientForm") {
            @Override
            protected void onSubmit() {
                PatientResource patient = PatientResource.builder()
                        .name(getAndResetObject(inputNameModel))
                        .gender(Gender.valueOf(getAndResetObject(inputGenderModel).toUpperCase()))
                        .build();

                patientService.create(patient);
            }
        };

        addPatientForm.add(new TextField("inputName", inputNameModel));
        addPatientForm.add(new DropDownChoice<>("inputGender", inputGenderModel, inputGenderOptions));

        return addPatientForm;
    }

    private String getAndResetObject(PropertyModel<String> propertyModel) {
        String object = propertyModel.getObject();
        propertyModel.setObject(null);
        return object;
    }
}
