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
        salt = PasswordUtils.generateSalt();
        hash_pwd = PasswordUtils.hashPassword(p_pwd,salt);
    }

    /**
     * Crea un utente {id = p_id,password=""}
     * @requires p_id != null
     * @throws IllegalArgumentException se p_id = null
     */
    public User(String p_id) throws IllegalArgumentException{
        if(p_id == null) throw new IllegalArgumentException("p_id must be != null !");
        id = p_id;
        hash_pwd = "";
    }

    /**
     * Check if candidatePwd is the correct pwd for this
     * @requires p_id != null
     * @throws IllegalArgumentException se p_id = null
     * @return true se la password è corretta, altrimenti false
     */
    public boolean auth(String candidatePwd) throws IllegalArgumentException{
        if(candidatePwd == null) throw new IllegalArgumentException("candidatePwd must be != null !");
        return PasswordUtils.verifyPassword(candidatePwd,hash_pwd,salt);
    }

    public String getId(){
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            User u = (User) obj;
            return u.id.equals(id);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String toString(){
        return id;
    }
}
