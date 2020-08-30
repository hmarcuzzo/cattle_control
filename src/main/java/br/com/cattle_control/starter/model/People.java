package br.com.cattle_control.starter.model;

import java.io.Serializable;

public class People implements Serializable, Comparable<People>{
    
    private Integer id;
    private Integer type;
    private Integer id_type;
    private String  name;
    private String  email;
    private Integer phone;
    private String  info;
    private Boolean deleted;


    public People() {
    }

    public People(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public Integer getIdType() {
        return id_type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getInfo() {
        return info;
    }

    public Boolean getDeleted() {
        return deleted;
    }


    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setIdType(Integer id_type) {
        this.id_type = id_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    

    public People type(Integer type) {
        this.type = type;
        return this;
    }

    public People idType(Integer id_type) {
        this.id_type = id_type;
        return this;
    }

    public People name(String name) {
        this.name = name;
        return this;
    }

    public People email(String email) {
        this.email = email;
        return this;
    }

    public People phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public People info(String info) {
        this.info = info;
        return this;
    }

    public People deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        People other = (People) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public boolean hasModel() {
        return model != null && !"".equals(model.trim());
    }

    public boolean hasName() {
        return name != null && !"".equals(name.trim());
    }

    @Override
    public int compareTo(People o) {
        return this.id.compareTo(o.getId());
    }
}