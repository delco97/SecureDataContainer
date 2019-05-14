/**
 * Overview:
 * User è un tipo di dato contiene le informazioni realtive ad un utente.
 * Il valore di un tipico utente è:
 *      u = {id,password}
 */
public class User {
    private String id;
    private String hash_pwd;
    private String salt;

    /**
     * Crea un utente {id = p_id,password=p_pwd}
     * @requires p_id != null && p_pwd != null
     * @throws IllegalArgumentException se p_id = null || p_pwd = null
     */
    public User(String p_id, String p_pwd) throws IllegalArgumentException{
        if(p_id == null) throw new IllegalArgumentException("p_id must be != null !");
        if(p_pwd == null) throw new IllegalArgumentException("p_id must be != null !");
        id = p_id;
        hash_pwd = p_pwd;
    }

}
