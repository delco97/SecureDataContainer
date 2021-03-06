import java.util.Iterator;

/**
 * Overview:
 * ISecureDataContainer<E> è un contenitore modificabile di oggetti di tipo E. Intuitivamente la collezione si comporta come
 * una specie Data Storage per la memorizzazione e condivisione di dati (rappresentati nella simulazione da
 * oggetti di tipo E). La collezione garantisce un meccanismo di sicurezza dei dati fornendo un proprio
 * meccanismo di gestione delle identità degli utenti. Inoltre, la collezione fornisce un meccanismo di
 * controllo degli accessi che permette al proprietario del dato di eseguire una restrizione selettiva
 * dell'accesso ai suoi dati inseriti nella collezione. Alcuni utenti possono essere autorizzati dal proprietario
 * ad accedere ai dati, mentre altri non possono accedervi senza autorizzazione.
 *
 *  Abstartc state:
 *  Un tipico ISecureDataContainer<E> è costituito dai seguenti elementi:
 *      U = {u0,u1,...,un-1} è un insieme di n utenti dove:
 *          ui = {id,password} && Not Exist (i,j) con 0 <= i < n && con 0 <= j < n . ui.id = uj.id
 *
 *      D = [d0,d1,...,dm-1 è una collezione di m elementi di tipo E
 *
 *      Owner = {(d,u) | d appartiene a D && u appartiene a U && Owner(d) = u}
 *      E' una relazione che associa a ciascun elemento d di D un elemento di U.
 *      Owner(d) = u => Il proprietario di d è u
 *
 *      Access = {(d,U') | d appartiene a D && U' è un sottoinsieme di U && Access(d) = u}
 *      E' una relazione che associa a ciascun elemento d di D un elemento di U.
 *      Owner(d) = U' => Utenti in U' hanno accesso a d
 *
 */
public interface ISecureDataContainer<E>{

    /**
     * Crea l’identità di un nuovo utente della collezione
     * @requires Id != null && passw != null && Not (Exist u appartenente a U tale che u.id = Id)
     * @throws NullPointerException se Id = null || passw = null
     * @throws UserException se (Exist u appartenente a U tale che u.id = Id)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U + u
     */
    void createUser(String Id, String passw) throws NullPointerException, UserException;

    /**
     * Rimuove l’utente dalla collezione
     * @requires Id != null && passw != null && (Exist u appartenente a U tale che u.id = Id && u.password = passw)
     * @throws IllegalArgumentException se Id = null || passw = null
     * @throws UserException Not (Exist u appartenente a U tale che u.id = Id && u.password = passw)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U - u && this_post.D = this_pre.D - this_pre.Owner(u)
     */
    void removeUser(String Id, String passw) throws NullPointerException, UserException;

    /**
     * Restituisce il numero degli elementi di un utente presenti nella
     * collezione
     * @requires Owner != null && passw != null && (Exist u appartenente a U tale che u.id = Owner && u.password = passw)
     * @throws NullPointerException se Owner = null || passw = null
     * @throws UserException Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw)
     * @return restituisce 0 <= |Owner(u)|<= |D|
     */
    int getSize(String Owner, String passw) throws IllegalArgumentException;

    /**
     * Inserisce il valore del dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && data != null &&
     *           (Exist u appartenente a U tale che u.id = Owner && u.password = passw && data Not in Owner(u))
     * @throws IllegalArgumentException se Owner = null || passw = null || data = null ||
     *         Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw ) || data in D
     * @effects Se this_pre.Owner(u) non contiene allora this_post.Owner(u) contiene data; altrimenti this_post.Owner(u) = this_pre.Owner(u)
     * @return restituisce true se data viene inserito, false altrimenti.
     */
    boolean put(String Owner, String passw, E data) throws IllegalArgumentException;


    /**
     * Ottiene una copia del valore del dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Id != null && passw != null && data != null && (Exist u appartenente a U tale che u.id = Id && u.password = passw && Access(u) contiene data)
     * @throws IllegalArgumentException se Id = null || passw = null || data = null ||
     *         Not (Exist u appartenente a U tale che u.id = Id && u.password = passw && Access(u) contiene data)
     * @return restituisce una copia data se data è presente all'interno di this; null altrimenti
     */
    E get(String Id, String passw, E data) throws IllegalArgumentException;

    /**
     * Rimuove il dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && data != null && (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data)
     * @throws IllegalArgumentException se Owner = null || passw = null || data = null || Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw)||
     *         Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data)
     * @modifies this
     * @effects this_post.D = this_pre.D - data
     * @return restituisce una deep copy di data prima di rimuoverlo da D
     */
    E remove(String Owner, String passw, E data) throws IllegalArgumentException;

    /**
     * Crea una copia del dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && data != null && (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data)
     * @throws IllegalArgumentException se Owner = null || passw = null || data = null || Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw)||
     *         Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data)
     * @modifies this
     * @effects this_post.D = this_pre.D + data' dove data' è una deep copy di data
     */
    void copy(String Owner, String passw, E data) throws IllegalArgumentException;

    /**
     * Condivide il dato nella collezione con un altro utente
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && Other != null && data != null && (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data) &&
     *           (Exist u appartenente a U tale che u.id = Other)
     * @throws IllegalArgumentException se Owner = null || passw = null || Other = null || data = null ||
     *         Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw && Owner(u) contiene data) ||
     *         Not (Exist u appartenente a U tale che u.id = Other)
     * @modifies this
     * @effects this_post.D = this_pre.D + data' dove data' è una deep copy di data
     */
    void share(String Owner, String passw, String Other, E data) throws IllegalArgumentException;

    /**
     * Restituisce un iteratore (senza remove) che genera tutti i dati
     * dell’utente in ordine arbitrario
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && (Exist u appartenente a U tale che u.id = Owner && u.password = passw)
     * @throws IllegalArgumentException se Owner = null || passw = null || Not (Exist u appartenente a U tale che u.id = Owner && u.password = passw)
     * @return restituisce una iteratore che genera tutti i dati dell’utente in ordine arbitrario
     */
    Iterator<E> getIterator(String Owner, String passw) throws IllegalArgumentException;

// … altre operazione da definire a scelta
    /**
     * Verifica se l'utente è gia presente
     * @requires Id != null
     * @throws IllegalArgumentException se Id = null
     * @return  Se (Exist u appartenente a U tale che u.id = Id) restituisce true, altrimenti false
     */
    boolean userExist(String id) throws IllegalArgumentException;
    /**
     * Verifica se le credenziali inserite sono quelle di un utente presente in this
     * @requires Id != null && passw != null
     * @throws IllegalArgumentException se Id = null || passw = null
     * @return  Se (Exist u appartenente a U tale che u.id = Id) restituisce true, altrimenti false
     */
    boolean userAuth(String id,String passw) throws IllegalArgumentException;
    /**
     * Verifica se l'utente id ha accesso al dato data
     * @requires Id != null && passw != null
     * @throws IllegalArgumentException se Id = null || passw = null
     * @return  Se (Exist u appartenente a U tale che u.id = Id) restituisce true, altrimenti false
     */
    boolean hasAccess(String id, String passw, E data);

}