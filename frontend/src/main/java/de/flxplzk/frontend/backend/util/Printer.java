package de.flxplzk.frontend.backend.util;

import de.flxplzk.frontend.backend.domain.Employee;

public interface Printer<T, O> {

    O print(T input);

    Printer<Employee, String> EMPLOYEE_STRING_PRINTER = input -> input.getFirstName() + " " + input.getLastName();
    Printer<Long, String> WORKING_TIME_PRINTER = input -> {
        long minutes = input % 60;
        long hours = (input - minutes) / 60;
        return hours + ":" + minutes;
    };
    Printer<String, String> EXCEL_FILE_POSTFIX_PRINTER = input -> input + ".xls";
}
