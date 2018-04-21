package de.flxplzk.frontend.backend.domain;

public enum TransactionType {

    SB("Stundenbuchung"),
    A("Ausgleich"),
    K("Krankheit"),
    T("Theorie"),
    U("Urlaub");

    private String name;

    private TransactionType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
