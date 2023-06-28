package se.klarna.business.customer.registry.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


public class Child implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private Date birthday;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
