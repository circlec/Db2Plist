package zc.dbtoplist;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "address_dict")
public class AddressEntity {
    private Long id;
    @Property(nameInDb = "parentId")
    private Long parentId;
    private String name;
    private Long code;

    @Generated(hash = 668391261)
    public AddressEntity(Long id, Long parentId, String name, Long code) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
    }

    @Generated(hash = 1750050641)
    public AddressEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
