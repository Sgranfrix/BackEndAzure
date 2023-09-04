package entities;


import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

@Container(containerName = "LazyHound")
public class User {

    @Id
    private String mail;
    @PartitionKey
    private String password;

    public User() {

    }

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return String.format("%s, %s", mail, password);
    }
}