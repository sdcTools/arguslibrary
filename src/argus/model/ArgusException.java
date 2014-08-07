package argus.model;
//import argus.model.Application;
import argus.utils.SystemUtils;

public class ArgusException extends Exception {
    
    private boolean critical;

    public ArgusException(boolean critical) {
        this.critical = critical;
    }

    public ArgusException(String message, boolean critical) {
        super(message);
        this.critical = critical;
        //if (Application.isBatch()) {
        SystemUtils.writeLogbook(message);
        //}
    }

    public ArgusException(String message) {
        this(message, true);
    }

    public ArgusException(String message, Throwable cause, boolean critical) {
        super(message, cause);
        this.critical = critical;
    }

    public ArgusException(Throwable cause, boolean critical) {
        super(cause);
        this.critical = critical;
    }

    public ArgusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, boolean critical) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.critical = critical;
    }

    public boolean getCritical() {
        return critical;
    }
}
