package mastermind.safe;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class MasterMindAlphanumericSafe extends MasterMindSafeAbstract<String> {

    public MasterMindAlphanumericSafe(Integer numberOfDigits) {
        super(numberOfDigits);
    }

    @Override
    public String getRegexPattern() {
        return "[\\w|\\d]{" + getNumberOfChar() + "}";
    }

    @Override
    protected List<String> generateSafeCodes() {
        // Genero una lista di elementi con solo caratteri alfanumerici
        String random = RandomStringUtils.randomAlphanumeric(getNumberOfChar());
        return List.of(random.toLowerCase().split(""));
    }

    @Override
    protected String convertStringToElement(String element) {
        return element;
    }
}
