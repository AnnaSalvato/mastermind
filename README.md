# MasterMind
Implementazione semplice del gioco MasterMind

## Istruzioni

Nel Main.java è possibile cambiare sia il numero di elementi da indovinare che la "tipologia" di gioco.

### Numero di elementi
Per cambiare il numero di elementi da indovinare è sufficiente modificare il valore della variabile statica NUMBER_OF_DIGIT.

### Tipologia di gioco
È possibile scegliere fra 3 diverse tipologie di gioco:
- Numerico: la combinazione sarà composta da solo numeri interi
- Alfabetico: la combinazione sarà composta solo da lettere (case insensitive)
- Alfanumerico: la combinazione sarà composta sia da lettere (case insensitive) che da numeri interi

Per cambiare la tipologia di gioco è sufficiente nel Main.java cambiare l'enum al secondo parametro del costruttore di PlayGame.
