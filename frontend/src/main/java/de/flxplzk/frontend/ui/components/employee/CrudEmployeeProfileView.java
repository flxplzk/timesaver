package de.flxplzk.frontend.ui.components.employee;

import com.vaadin.shared.Registration;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.vaadin.mvvm.ItemBound;

import de.flxplzk.frontend.ui.common.NumberTextFieldField;
import de.flxplzk.vaadin.mvvm.OnClick;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

@View(model = "crudEmployeeProfileViewModel")
public class CrudEmployeeProfileView extends Window{
    private ViewModelComposer viewModelComposer;

    @ItemBound(to = "profileName")
    private final TextField mProfileNameTextField = new TextField("Profilname:");

    @ItemBound(to = "isPermanentEmployed")
    private final CheckBox mPermanentEmployedCheckBox = new CheckBox("Festangestellt?");

    @ItemBound(to = "weeklyHours")
    private final NumberTextFieldField mWeeklyHoursTextField = new NumberTextFieldField("Wochenarbeitszeit:");

    @ItemBound(to = "yearlyHoliday")
    private final NumberTextFieldField mYearlyHolidayTextField = new NumberTextFieldField("Jährlicher Urlaub:");

    @OnClick(method = "save", callback = "close")
    private final Button mSaveButton = new Button("Speichern");
    private Registration registration;

    public CrudEmployeeProfileView(ViewModelComposer viewModelComposer) {
        this.viewModelComposer = viewModelComposer;
        build();
        bind();
    }

    private void build() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(this.mProfileNameTextField, this.mPermanentEmployedCheckBox, this.mWeeklyHoursTextField, this.mYearlyHolidayTextField, this.mSaveButton);
        style();
        setCaption("Mitarbeiterprofil anlegen ...");
        setContent(layout);
        setModal(true);
        setClosable(true);
        setResizable(false);
    }
    private void style() {
        this.mProfileNameTextField.setPlaceholder("Name ...");
        this.mWeeklyHoursTextField.setPlaceholder("Wochenstunden ...");
        this.mYearlyHolidayTextField.setPlaceholder("Urlaubstage ...");
        this.mSaveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
    }

    @Override
    public void close() {
        super.close();
        unbind();
    }

    private void bind() {
        this.registration = this.viewModelComposer.bind(this);
    }

    private void unbind(){
        this.registration.remove();
    }
}
