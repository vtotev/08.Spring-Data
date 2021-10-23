package com.example.xmlprocessing_exc.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersCountRootDto {
    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "user")
    private List<UsersCountDto> users;

    public UsersCountRootDto() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UsersCountDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersCountDto> users) {
        this.users = users;
    }
}
