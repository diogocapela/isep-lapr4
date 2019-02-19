package georef.sg01;

public interface SG01Test {

    void itShouldThrowAnExceptionWhenPostalCodeIsNull();

    void itShouldWorkForKnownPostalCodeNumberOne();

    void itShouldWorkForKnownPostalCodeNumberTwo();

    void itShouldReturnNullForUnknownPostalCode();

}
