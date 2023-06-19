package mastermind.game;

import mastermind.safe.MasterMindSafeAbstract;
import mastermind.user_attempt.UserAttemptResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class PlayGame {

    private final MasterMindSafeAbstract<?> safe;

    public PlayGame(Integer numberOfDigits, TypeOfGameEnum type) {
        // Recupero la giusta implementazione di MasterMindSafeAbstract sulla base del tipo di gioco impostato.
        // Inoltre nel costruttore imposto già il codice cassaforte
        safe = MasterMindSafeAbstract.getFactory(numberOfDigits, type);
        System.out.println("(... Il codice cassaforte generato è : " + safe.getSafeCodes().stream().map(i->i.toString()).collect(Collectors.joining(", ")) + "...)");
    }

    public void startGame() {
        do {
            // Leggo la combinazione inserita dall'utente
            String userAttemptString = readUserAttempt();
            if (userAttemptString == null) {
                // Si è arreso
                System.out.println("Andrà meglio la prossima volta! Il codice cassaforte era " + safe.getSafeCodes());
                break;
            }
            System.out.println("Il codice da te inserito è il " + userAttemptString);

            // Verifico il match
            UserAttemptResult<?> userResult = safe.checkUserAttempt(userAttemptString);
            if (userResult.didUserWin()) {
                System.out.println("Grande! Hai indovinato la combinazione vincente!");
                break;
            } else {
                System.out.println("Hai indovinato " + userResult.getNumberOfPerfectMatch() + " elementi in posizione corretta e " + userResult.getNumberOfPartialMatch() + " elementi in posizione errata");
                System.out.println("Riprova!");
            }
        } while (true);
    }

    private String readUserAttempt() {
        String numberString;
        boolean regexMatches;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println("Inserisci i " + safe.getNumberOfChar() + " caratteri (" + safe.getRegexPattern() + ") che pensi siano la soluzione (per arrendersi premere INVIO): ");
            try {
                numberString = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (numberString.isBlank()) {
                return null; // si è arreso
            }

            regexMatches = numberString.matches(safe.getRegexPattern());

            if (!regexMatches) {
                System.out.println("Bisogna inserire necessariamente " + safe.getNumberOfChar() + " caratteri con regex " + safe.getRegexPattern() + ". Riprovare.");
            }

        } while (!regexMatches);

        return numberString.toLowerCase();
    }
}
