import java.util.Iterator;

public interface SecureDataContainer<E>{
    /**
        Overview:
        SecureDataContainer è un contenitore modificabile di oggetti di tipo E. Intuitivamente la collezione si comporta come
        una specie Data Storage per la memorizzazione e condivisione di dati (rappresentati nella simulazione da
        oggetti di tipo E). La collezione garantisce un meccanismo di sicurezza dei dati fornendo un proprio
        meccanismo di gestione delle identità degli utenti. Inoltre, la collezione fornisce un meccanismo di
        controllo degli accessi che permette al proprietario del dato di eseguire una restrizione selettiva
        dell'accesso ai suoi dati inseriti nella collezione. Alcuni utenti possono essere autorizzati dal proprietario
        ad accedere ai dati, mentre altri non possono accedervi senza autorizzazione.

     */

    // Crea l’identità un nuovo utente della collezione
    void createUser(String Id, String passw);

    // Rimuove l’utente dalla collezione
    void removeUser(String Id, String passw);

    // Restituisce il numero degli elementi di un utente presenti nella
    // collezione
    int getSize(String Owner, String passw);

    // Inserisce il valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    boolean put(String Owner, String passw, E data);

    // Ottiene una copia del valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    E get(String Owner, String passw, E data);

    // Rimuove il dato nella collezione
    // se vengono rispettati i controlli di identità
    E remove(String Owner, String passw, E data);

    // Crea una copia del dato nella collezione
    // se vengono rispettati i controlli di identità
    void copy(String Owner, String passw, E data);

    // Condivide il dato nella collezione con un altro utente
    // se vengono rispettati i controlli di identità
    void share(String Owner, String passw, String Other, E data);

    // restituisce un iteratore (senza remove) che genera tutti i dati
    // dell’utente in ordine arbitrario
    // se vengono rispettati i controlli di identità
    Iterator<E> getIterator(String Owner, String passw);

// … altre operazione da definire a scelta
}