package mastermind.safe;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;

public class MasterMindAlphabeticSafe extends MasterMindSafeAbstract<String> {

    public MasterMindAlphabeticSafe(Integer numberOfDigits) {
        super(numberOfDigits);
    }

    @Override
    public String getRegexPattern() {
        return "\\w{" + getNumberOfChar() + "}";
    }

    @Override
    protected List<String> generateSafeCodes() {
        // Genero una lista di elementi con solo caratteri alfabetici
        String random = RandomStringUtils.randomAlphabetic(getNumberOfChar());
        return List.of(random.toLowerCase().split(""));
    }

    @Override
    protected String convertStringToElement(String element) {
        return element;
    }
}
