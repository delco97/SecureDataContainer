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
 *    U = c.owners.keySet
 *    D = c.access.keySet
 *
 *    For all u in U. Owner(u) = c.owners(u)
 *    For all d in D. Access(d) = c.access(d)
 *
 *  IR:
 *      owners != null && access != null &&
 *      For all d in access.keySet (Exist D' in owner.values che contiene d) &&
 *      For all u in owner.keySet (Exist U' in access.values che contiene u)
 */
public class MapSecureDataContainer<E> implements ISecureDataContainer {
    private Map<User,Set<E>> owners;
    private Map<E, Set<User>> access;


    public MapSecureDataContainer(){
        owners = new HashMap<>();
        access = new HashMap<>();
    }

    /**
     * Crea l’identità di un nuovo utente della collezione
     * @requires Id != null && passw != null && Not (Exist u appartenente a U tale che u.id = Id)
     * @throws IllegalArgumentException se Id = null || passw = null || (Exist u appartenente a U tale che u.id = Id)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U + u
     */
    @Override
    public void createUser(String Id, String passw) throws IllegalArgumentException {
        if(Id == null) throw new IllegalArgumentException("Id must be != null !");
        if(passw == null) throw new IllegalArgumentException("passw must be != null !");
        if(userExist(Id)) throw new IllegalArgumentException("users id must be unique !");
        owners.put(new User(Id,passw), new HashSet<>());
    }
    /**
     * Rimuove l’utente dalla collezione
     * @requires Id != null && passw != null && (Exist u appartenente a U tale che u.id = Id && u.password = passw)
     * @throws IllegalArgumentException se Id = null || passw = null || Not (Exist u appartenente a U tale che u.id = Id && u.password = passw)
     * @modifies this
     * @effects u = {Id,passw} && this_post.U = this_pre.U - u && this_post.D = this_pre.D - this_pre.Owner(u)
     */
    @Override
    public void removeUser(String Id, String passw) throws IllegalArgumentException {
        if(Id == null) throw new IllegalArgumentException("Id must be != null !");
        if(passw == null) throw new IllegalArgumentException("passw must be != null !");
        if(userAuth(Id,passw)) throw new IllegalArgumentException("valid users' credentials are required !");
        access.values().remove(owners.remove(new User(Id)));
    }

    @Override
    public int getSize(String Owner, String passw) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public boolean put(String Owner, String passw, Object data) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Object get(String Id, String passw, Object data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Object remove(String Owner, String passw, Object data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, Object data) throws IllegalArgumentException {

    }

    @Override
    public void share(String Owner, String passw, String Other, Object data) throws IllegalArgumentException {

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
     * @return  Se (Exist u appartenente a U tale che u.id = Id) restituisce true, altrimenti false
     */
    @Override
    public boolean userAuth(String id, String passw) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException("Id must be != null !");
        for (User u : owners.keySet()) {
            if (u.getId().equals(id)) {
                return u.auth(passw);
            }
        }
        return false;
    }


}
