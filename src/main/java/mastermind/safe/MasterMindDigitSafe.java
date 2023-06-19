package mastermind.safe;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Preparazione della "cassaforte"
 */
public class MasterMindDigitSafe extends MasterMindSafeAbstract<Integer> {

    public MasterMindDigitSafe(Integer numberOfDigits) {
        super(numberOfDigits);
    }


    @Override
    public String getRegexPattern() {
        return "\\d{" + getNumberOfChar() + "}";
    }

    @Override
    public List<Integer> generateSafeCodes() {
        // Genero una lista di elementi con solo caratteri numerici
        return new Random().ints(getNumberOfChar(), 0, 10).boxed().collect(Collectors.toList());
    }

    @Override
    protected Integer convertStringToElement(String element) {
        return Integer.parseInt(element);
    }

}
