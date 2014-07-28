package argus.model;

public enum Type {
//  type                   description     micro  tab    num
    CATEGORICAL            ("explanatory", true,  true,  false ),
    RESPONSE               ("response",    true,  true,  true  ),
    CAT_RESP               ("exp./resp.",  true,  false, true  ),
    WEIGHT                 ("weight",      true,  false, true  ),
    HOLDING                ("holding",     true,  false, false ),
    REQUEST                ("request",     true,  false, false ),
    SHADOW                 ("shadow",      false, true,  true  ),
    COST                   ("cost",        false, true,  true  ),
    FREQUENCY              ("frequency",   false, true,  true  ),
    TOP_N                  ("top N",       false, true,  true  ),
    LOWER_PROTECTION_LEVEL ("lower PL",    false, true,  true  ),
    UPPER_PROTECTION_LEVEL ("upper PL",    false, true,  true  ),
    STATUS                 ("status",      false, true,  false );
    
    private final String description;
    private final boolean availableForMicrodata;
    private final boolean availableForTabularData;
    private final boolean numeric;

    private Type(String description, boolean availableForMicrodata, boolean availableForTabularData, boolean numeric) {
        this.description = description;
        this.availableForMicrodata = availableForMicrodata;
        this.availableForTabularData = availableForTabularData;
        this.numeric = numeric;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailableForMicrodata() {
        return availableForMicrodata;
    }

    public boolean isAvailableForTabularData() {
        return availableForTabularData;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public boolean hasDecimals() {
        return isNumeric() && this != FREQUENCY;
    }

    public boolean isCategorical() {
        return this == CATEGORICAL || this == CAT_RESP;
    }
    
    public boolean isResponse() {
        return this == RESPONSE || this == CAT_RESP;
    }
}
