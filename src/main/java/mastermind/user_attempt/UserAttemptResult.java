package mastermind.user_attempt;

import java.util.List;

public class UserAttemptResult<T> {


    private List<T> userAttempt;
    private int numberOfPerfectMatch; // Numero di elementi in posizione corretta
    private int numberOfPartialMatch; // Numero di elementi corretti ma posizione sbagliata

    public UserAttemptResult(List<T> userAttempt) {
        this.userAttempt = userAttempt;
        this.numberOfPerfectMatch = 0;
        this.numberOfPartialMatch = 0;
    }

    public List<T> getUserAttempt() {
        return userAttempt;
    }

    public int getNumberOfPerfectMatch() {
        return numberOfPerfectMatch;
    }

    public int getNumberOfPartialMatch() {
        return numberOfPartialMatch;
    }

    public void addPerfectMatches(int numberOfMatch) {
        numberOfPerfectMatch += numberOfMatch;
    }

    public void addPartialMatch(int numberOfMatch) {
        numberOfPartialMatch += numberOfMatch;
    }

    public boolean didUserWin() {
        return numberOfPerfectMatch == userAttempt.size();
    }
}
