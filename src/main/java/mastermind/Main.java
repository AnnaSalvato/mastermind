package mastermind;

import mastermind.game.PlayGame;
import mastermind.game.TypeOfGameEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final Integer NUMBER_OF_DIGIT = 5;


    public static void main(String[] args) {
        System.out.println("Benvenuto in MasterMind! Pronto a giocare?");

        boolean nextGame = true;

        do {
            // Ogni PlayGame rappresenta una partita.
            // Posso avere 3 modalità di gioco:
            // TypeOfGameEnum.NUMERIC --> solo numeri interi
            // TypeOfGameEnum.ALPHABETIC --> solo lettere (case insensitive)
            // TypeOfGameEnum.ALPHANUMERIC --> sia numeri interi che lettere (case insensitive)
            // Nel costruttore costruisco il codice cassaforte (ovvero il codice da scoprire)
            PlayGame game = new PlayGame(NUMBER_OF_DIGIT, TypeOfGameEnum.NUMERIC);
            // Inizio vero e proprio del gioco dove l'utente inserisce le sue combinazioni
            game.startGame();

            // Una volta terminato il gioco, posso scegliere di fare una nuova partita
            System.out.println("Vuoi fare un'altra partita? Digita un carattere qualsiasi. Per uscire premi INVIO");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String numberString = br.readLine();
                if (numberString.equals("")) {
                    nextGame = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (nextGame);


    }
}
