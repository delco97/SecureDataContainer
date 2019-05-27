import java.util.*;

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
 *  AF:
 *    D = c.els
 *    U = c.users
 *
 *
 *    For 0 =< i < c.els.size.  c.owners(i) is in U && c.owners(i) = Owners(di) dove di è il dato in posizione i all'interno di c.els
 *    For 0 =< i < c.els.size.  c.access(i) è un sottoinsieme di U && c.access(i) = Access(di) dove di è il dato in posizione i all'interno di c.els
 *
 *  IR:
 *      owners != null && access != null && els!=null && users!=null
 *      For all d in access.keySet (Exist D' in owner.values che contiene d) &&
 *      For all u in owner.keySet (Exist U' in access.values che contiene u)
 */
//TODO: rivedi specifiche
public class MapSecureDataContainer<E> implements ISecureDataContainer<E> {
    //private Map<User,Set<E>> owners;
    //private Map<E, Set<User>> access;
    private Set<User> users;
    private List<E> els;
    private List<User> owners;
    private List<Set<User>> access;

    public MapSecureDataContainer(){
        els = new ArrayList<>();
        users = new HashSet<>();
        owners = new ArrayList<>();
        access = new ArrayList<>();
    }

    /**
     * Crea l’identità di un nuovo utente della collezione
     * @requires Id != null && passw != null && Not (users contiene u tale che u.id = Id)
     * @throws NullPointerException se Id = null || passw = null
     * @throws UserException (users contiene u tale che u.id = Id)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U + u
     */
    @Override
    public void createUser(String Id, String passw) throws NullPointerException, UserException {
        if(Id == null) throw new NullPointerException("Id must be != null !");
        if(passw == null) throw new NullPointerException("passw must be != null !");
        if(users.add(new User(Id,passw))) throw new UserException("users inserted must be unique!");
    }
    /**
     * Rimuove l’utente dalla collezione
     * @requires Id != null && passw != null && (users contiene u tale che u.id = Id && u.password = passw)
     * @throws NullPointerException se Id = null || passw = null
     * @throws UserException se Not (users contiene u tale che u.id = Id && u.password = passw)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U - u && this_post.D = this_pre.D - this_pre.Owner(u)
     */
    @Override
    public void removeUser(String Id, String passw) throws NullPointerException, UserException {
        if(Id == null) throw new NullPointerException("Id must be != null !");
        if(passw == null) throw new NullPointerException("passw must be != null !");
        if( !users.remove(new User(Id,passw))) throw new UserException("User" + Id + " not exist or pwd inserted is not valid!");
        //Utente è stato rimosso.
        int pos = owners.indexOf(Id);
        while (pos != -1){
            owners.remove(pos);
            els.remove(pos);
            access.get(pos).remove(Id);
            pos = owners.indexOf(Id);
        }
    }
    /**
     * Restituisce il numero degli elementi di un utente presenti nella
     * collezione
     * @requires Owner != null && passw != null && (users contiene u tale che u.id = Id && u.password = passw)
     * @throws NullPointerException se Owner = null || passw = null
     * @throws UserException Not (users contiene u tale che u.id = Id && u.password = passw)
     * @return restituisce il numero posseduti da un utente
     */
    @Override
    public int getSize(String Owner, String passw) throws NullPointerException, UserException {
        if(Owner == null) throw new NullPointerException("Id must be != null !");
        if(passw == null) throw new NullPointerException("passw must be != null !");
        if(!userAuth(Owner,passw)) throw new UserException("valid users' credentials are required !");
        return Collections.frequency(owners, Owner);
    }

    /**
     * Inserisce il valore del dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Owner != null && passw != null && els != null &&
     *           (users contiene u tale che u.id = Id && u.password = passw) && els Not in D
     * @throws NullPointerException se Owner = null || passw = null || els = null
     * @throws UserException  Not (users contiene u tale che u.id = Id && u.password = passw) || els in D
     * @effects Se this_pre.Owner(u) non contiene allora this_post.Owner(u) contiene els; altrimenti this_post.Owner(u) = this_pre.Owner(u)
     * @return restituisce true se els viene inserito, false altrimenti.
     */
    @Override
    public boolean put(String Owner, String passw, E data) throws NullPointerException {
        if(Owner == null) throw new NullPointerException("Owner must be != null !");
        if(passw == null) throw new NullPointerException("passw must be != null !");
        if(data == null) throw new NullPointerException("els must be != null !");
        if(!userAuth(Owner,passw)) return false;
        User u = new User(Owner,passw);
        Set<User> s = new HashSet<>();
        s.add(u);

        els.add(data);
        owners.add(u);
        access.add(s);
        return true;
    }
    /**
     * Ottiene una copia del valore del dato nella collezione
     * se vengono rispettati i controlli di identità
     * @requires Id != null && passw != null && els != null && (users contiene u tale che u.id = Id && u.password = passw && Access(u) contiene els)
     * @throws IllegalArgumentException se Id = null || passw = null || els = null ||
     * @throws UserException Not (users contiene u tale che u.id = Id && u.password = passw && Access(u) contiene els)
     * @return restituisce una copia els se els è presente all'interno di this; null altrimenti
     */
    @Override
    public E get(String Id, String passw, E data) throws IllegalArgumentException {
        if(Id == null) throw new NullPointerException("Id must be != null !");
        if(passw == null) throw new NullPointerException("passw must be != null !");
        if(data == null) throw new NullPointerException("els must be != null !");
        if(!userAuth(Id,passw)) throw new UserException("valid users' credentials are required !");
        if(hasAccess(Id,passw,data))


        if(!access.containsKey(data)) return null;
        return data;
    }


    @Override
    public E remove(String Owner, String passw, E data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) throws IllegalArgumentException {

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) throws IllegalArgumentException {

    }

    @Override
    public Iterator getIterator(String Owner, String passw) throws IllegalArgumentException {
        return null;
    }

    /**
     * Verifica se l'utente è gia presente
     * @requires Id != null
     * @throws IllegalArgumentException se Id = null
     * @return  Se (Exist u appartenente a U tale che u.id = Id) restituisce true, altrimenti false
     */
    @Override
    public boolean userExist(String id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException("Id must be != null !");
        return owners.keySet().contains(new User(id));
    }

    /**
     * Verifica se le credenziali inserite sono quelle di un utente presente in this
     * @requires Id != null && passw != null
     * @throws IllegalArgumentException se Id = null || passw = null
     * @return  Se (Exist u appartenente a U tale che u.id = Id && u.password = passw) restituisce true, altrimenti false
     */
    @Override
    public boolean userAuth(String id, String passw) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException("Id must be != null !");
        if(passw == null) throw new IllegalArgumentException("passw must be != null !");
        for (User u : owners.keySet()) {
            if (u.getId().equals(id)) {
                return u.auth(passw);
            }
        }
        return false;
    }


}
