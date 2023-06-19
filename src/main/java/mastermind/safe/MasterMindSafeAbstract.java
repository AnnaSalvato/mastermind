package mastermind.safe;

import mastermind.game.TypeOfGameEnum;
import mastermind.user_attempt.UserAttemptResult;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MasterMindSafeAbstract<T> {

    private Integer numberOfChar; // Numero di elementi
    private List<T> safeCodes; // Cassaforte, oggetto della soluzione composta dagli N elementi da indovinare

    private Map<T, Set<Integer>> safeCodesMaps; // Mappa della combinazione, la chiave è il carattere digitato e il valore è il set delle sue posizioni


    public static MasterMindSafeAbstract<?> getFactory(Integer numberOfDigit, TypeOfGameEnum typeOfGame) {
        // Recupero la clase corretta sulla base del tipo di gioco iniziato
        switch (typeOfGame) {
            case NUMERIC: return new MasterMindDigitSafe(numberOfDigit);
            case ALPHABETIC: return new MasterMindAlphabeticSafe(numberOfDigit);
            case ALPHANUMERIC: return new MasterMindAlphanumericSafe(numberOfDigit);
        }
        throw new RuntimeException("Play method not implemented");
    }


    public MasterMindSafeAbstract(Integer numberOfDigits) {
        numberOfChar = numberOfDigits; // Numero di elementi da indovinare
        safeCodes = generateSafeCodes(); // Genero il codice cassaforte
        generateSafeCodesMap(); // Preparo la mappa del codice cassaforte che mi servirà per determinare quanti elementi indovina l'utente
    }

    public UserAttemptResult<T> checkUserAttempt(String userAttempt) {
        // Trasformo la stringa inserita dall'utente in una lista di elementi
        List<T> userAttemptList = Arrays
                .stream(userAttempt.split(""))
                .map(this::convertStringToElement)
                .collect(Collectors.toList());

        // Preparo il risultato
        UserAttemptResult<T> attemptResult = new UserAttemptResult<>(userAttemptList);

        // Preparo la mappa degli elementi dell'utente (come per la cassaforte).
        // La mappa sarà quindi così composta:
        // - chiave: l'elemento inserito dall'utente
        // - valore: set di indici che sono gli indici del tentativo utente in cui si trovano gli elementi con quella chiave
        Map<T, Set<Integer>> userAttemptMap = generateUserAttemptMap(userAttemptList);

        // Per ogni chiave della mappa utente (ovvero ogni elemento distinto...)
        for (T element : userAttemptMap.keySet()) {
            // Recupero le posizioni in cui si trovava questo elemento inserito dall'utente
            Set<Integer> attemptIndexSet = userAttemptMap.get(element);
            if (!safeCodesMaps.containsKey(element)) {
                // Se l'elemento digitato dall'utente non è presente all'interno della cassaforte (soluzione) significa che ha inserito un numero sbagliato
                continue;
            }
            // Altrimenti, significa che l'utente ha inserito il numero corretto e dobbiamo verificare quante volte e con che match (parziale o totale).
            // A tal fine, utilizzo l'intersezione dei 2 set di indici
            Set<Integer> intersection = new HashSet<Integer>(attemptIndexSet);
            intersection.retainAll(safeCodesMaps.get(element));
            // Se l'intersezione è vuota, significa che ho indovinato solo l'elemento e non la posizione
            // Se l'intersezione è piena, significa che per qualche elemento ho indovinato sia elemento che posizione
            int sizeOfIntersection = intersection.size();
            int sizeOfUserAttempt = attemptIndexSet.size();
            int sizeOfSafeCode = safeCodesMaps.get(element).size();
            // Se ci sono quindi elementi nell'intersezione, aggiungo la size al count del perfect match
            if (sizeOfIntersection > 0) {
                attemptResult.addPerfectMatches(sizeOfIntersection);
                // Tolgo il numero di volte di match perfetto
                sizeOfUserAttempt -= sizeOfIntersection;
                sizeOfSafeCode -= sizeOfIntersection;
            }
            // A questo punto, il numero di match parziali risulta essere la minore size fra le due rimaste
            if (sizeOfUserAttempt < sizeOfSafeCode) {
                attemptResult.addPartialMatch(sizeOfUserAttempt);
            } else {
                attemptResult.addPartialMatch(sizeOfSafeCode);
            }
        }
        return attemptResult;
    }


    public abstract String getRegexPattern();


    /**
     * Genera il codice cassaforte che l'utente dovrà indovinare
     * @return lista di elementi dove ogni elemento è una parte del codice cassaforte
     */
    protected abstract List<T> generateSafeCodes();

    /**
     * Converte l'elemento stringa ricevuto da input al tipo di elemento finale
     * @param element elemento inserito dall'utente
     * @return elemento inserito dall'utente convertito nel tipo corretto
     */
    protected abstract T convertStringToElement(String element);

    private void generateSafeCodesMap() {
        // Preparo la mappa di elementi del codice cassaforte.
        // La mappa sarà così composta:
        // - chiave: l'elemento della cassaforte
        // - valore: set di indici che sono gli indici della cassaforte in cui si trovano gli elementi con quella chiave
        safeCodesMaps = generateCodesMap(safeCodes);
    }

    private Map<T, Set<Integer>> generateUserAttemptMap(List<T> userAttempt) {
        return generateCodesMap(userAttempt);
    }

    private Map<T, Set<Integer>> generateCodesMap(List<T> codes) {
        Map<T, Set<Integer>> codesMap = new HashMap<>();
        for (int i = 0; i < codes.size(); i++) {
            T element = codes.get(i);
            if (!codesMap.containsKey(element)) {
                codesMap.put(element, new HashSet<>());
            }
            codesMap.get(element).add(i);
        }
        return codesMap;
    }

    public List<T> getSafeCodes() {
        return safeCodes;
    }

    public Integer getNumberOfChar() {
        return numberOfChar;
    }
}
