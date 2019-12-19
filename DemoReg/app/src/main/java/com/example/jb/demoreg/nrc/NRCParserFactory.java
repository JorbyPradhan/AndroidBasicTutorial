package com.example.jb.demoreg.nrc;

public class NRCParserFactory {
    private String command;
    NRC n;

    public NRCParserFactory(String command) {
        this.command = command;
    }

    public NRCParser getParser() {
       /* n = new NRC();
        n.nm;*/
        NRCParserEnum nrcFormatterEnum = NRCParserEnum.parse(command);
        switch (nrcFormatterEnum) {
            case ENGLISH:
                return new NRCParserEN();
            case MYANMAR:
                return new NRCParserMM();
            default:
                throw new IllegalArgumentException("The parser does not exist.");
        }
    }
}
