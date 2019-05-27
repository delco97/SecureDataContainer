import java.util.Objects;

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

    public String getHash_pwd() {
        return hash_pwd;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof User) {
            User user = (User) o;
            return id.equals(user.getId()) && hash_pwd.equals(user.getHash_pwd());
        }
        if (o instanceof String) {
            String userId = (String) o;
            return id.equals(userId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return id;
    }
}
