package georef.sg02;

public interface SG02Test {

    void ensureCorrectCity();

    void ensureCorrectCountry();

    void ensureCorrectDistrict();

    void ensureCorrectPostalCode();

    void itShouldThrowAnExceptionWhenPostalCodeIsNull();

    void itShouldReturnNullForUnknownPostalCode();

    void itShouldReturnNullForUnknowLocation();
}
