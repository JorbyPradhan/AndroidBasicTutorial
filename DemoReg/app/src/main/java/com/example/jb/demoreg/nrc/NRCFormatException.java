package com.example.jb.demoreg.nrc;

public class NRCFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    private NRCFormatError error;

    public NRCFormatException() {
        super();
    }

    public NRCFormatException(NRCFormatError error, String message) {
        super(message);
        this.error = error;
    }

    public NRCFormatException(NRCFormatError error) {
        super();
        this.error = error;
    }

    public NRCFormatError getError() {
        return error;
    }

    public void setError(NRCFormatError error) {
        this.error = error;
    }
}
