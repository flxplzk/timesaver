package de.flxplzk.frontend.ui.view.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.ui.common.NumberTextFieldField;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.ItemBound;
import de.flxplzk.vaadin.mvvm.OnClick;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

@View(model = "crudEmployeeProfileViewModel")
public class CrudEmployeeProfileViewComponent extends AbstractViewComponent{

    @ItemBound(to = "profileName")
    private final TextField mProfileNameTextField = new TextField("Profilname:");

    @ItemBound(to = "isPermanentEmployed")
    private final CheckBox mPermanentEmployedCheckBox = new CheckBox("Festangestellt?");

    @ItemBound(to = "weeklyHours")
    private final NumberTextFieldField mWeeklyHoursTextField = new NumberTextFieldField("Wochenarbeitszeit:");

    @ItemBound(to = "yearlyHoliday")
    private final NumberTextFieldField mYearlyHolidayTextField = new NumberTextFieldField("Jährlicher Urlaub:");

    @OnClick(method = "save")
    private final Button mSaveButton = new Button("Speichern");

    @OnClick(method = "cancel")
    private final Button mCancelButton = new Button("Abrechen");

    private VerticalLayout rootLayout = new VerticalLayout(
            this.mProfileNameTextField,
            this.mPermanentEmployedCheckBox,
            this.mWeeklyHoursTextField,
            this.mYearlyHolidayTextField,
            this.mSaveButton,
            this.mCancelButton
    );

    public CrudEmployeeProfileViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        build();
    }

    private void build() {
        style();
        setCaption("Mitarbeiterprofil anlegen ...");
        setCompositionRoot(rootLayout);
    }
    private void style() {
        this.rootLayout.setMargin(true);
        this.rootLayout.setSizeFull();

        this.mProfileNameTextField.setPlaceholder("Name ...");
        this.mProfileNameTextField.setSizeFull();
        this.mWeeklyHoursTextField.setPlaceholder("Wochenstunden ...");
        this.mWeeklyHoursTextField.setSizeFull();
        this.mYearlyHolidayTextField.setPlaceholder("Urlaubstage ...");
        this.mYearlyHolidayTextField.setSizeFull();
        this.mSaveButton.setSizeFull();
        this.mSaveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        this.mCancelButton.setSizeFull();
    }
}
