package de.flxplzk.frontend.ui.components.employee;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.*;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.vaadin.mvvm.*;

import java.util.ArrayList;
import java.util.List;

@View(model = "crudEmployeeViewModel")
public class CrudEmployeeView extends Window implements HasValue.ValueChangeListener {

    private final ViewModelComposer viewModelComposer;
    private List<Registration> registrations = new ArrayList<>();

    @ItemBound(to = "firstName")
    private final TextField mFirstName = new TextField("Vorname:");

    @ItemBound(to = "lastName")
    private final TextField mLastName = new TextField("Nachname:");

    @ItemBound(to = "employeeDate")
    private final DateField mEmployeeDate = new DateField("Einstellungsdatum:");

    @ItemBound(to = "selectedProfile")
    @ListingBound(to = "profiles")
    private final ComboBox<EmployeeProfile> profileComboBox = new ComboBox<>("Anstellungsart:");

    @OnClick(method = "save", callback = "close")
    private final Button mSaveButton = new Button("Speichern");
    private Registration registration;

    public CrudEmployeeView(ViewModelComposer viewModelComposer) {
        this.viewModelComposer = viewModelComposer;
        build();
        bind();
    }

    private void build() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(
                mFirstName,
                mLastName,
                mEmployeeDate,
                profileComboBox,
                mSaveButton);
        this.mSaveButton.setEnabled(false);
        setContent(layout);
        setModal(true);
        setResizable(false);
        setClosable(true);
        this.profileComboBox.setItemCaptionGenerator(EmployeeProfile::getProfileName);
    }

    @SuppressWarnings("unchecked")
    private void registerListeners() {
        registrations.add(this.mFirstName.addValueChangeListener(this));
        registrations.add(this.mLastName.addValueChangeListener(this));
        registrations.add(this.mEmployeeDate.addValueChangeListener(this));
        registrations.add(this.profileComboBox.addValueChangeListener(this));
    }

    @Override
    public void close() {
        super.close();
        this.unbind();
    }

    public void bind() {
        registration = this.viewModelComposer.bind(this);
        registerListeners();
    }

    public void unbind() {
        registration.remove();
        this.registrations.forEach(Registration::remove);
        this.registrations = new ArrayList<>();
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent event) {
        if (this.profileComboBox.getValue() == null
                || this.mLastName.getValue().equals("")
                || this.mFirstName.getValue().equals("")) {
            this.mSaveButton.setEnabled(false);
        } else {
            this.mSaveButton.setEnabled(true);
        }
    }
}
