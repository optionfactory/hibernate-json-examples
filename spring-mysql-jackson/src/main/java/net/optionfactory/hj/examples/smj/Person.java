package net.optionfactory.hj.examples.smj;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import net.optionfactory.hj.JsonType;
import org.hibernate.annotations.Type;

@Entity
public class Person {

    @Id
    private String name;

    @Type(type = JsonType.TYPE)
    @MyJsonColumn
    private List<String> hobbies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

}
