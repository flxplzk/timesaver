package de.flxplzk.frontend.ui.common;

import com.vaadin.data.HasValue;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class NumberTextFieldField extends TextField implements HasValue.ValueChangeListener<String>{

    private String previousValue = "0";

    public NumberTextFieldField() {
        super();
        addValueChangeListener(this);
    }

    public NumberTextFieldField(String caption) {
        super(caption);
        addValueChangeListener(this);
    }

    @Override
    public void valueChange(ValueChangeEvent<String> valueChangeEvent) {
        try {
            Integer.valueOf(getValue());
             previousValue = getValue();
        } catch (NumberFormatException numberException){
            setDescription("bitte nur Zahlen eingeben");
            setStyleName(ValoTheme.NOTIFICATION_ERROR);
        }
    }

    public int getNumberValueValue(){
        return Integer.valueOf(previousValue);
    }
}
