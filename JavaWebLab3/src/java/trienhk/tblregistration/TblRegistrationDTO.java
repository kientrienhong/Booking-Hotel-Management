/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblregistration;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Treater
 */
public class TblRegistrationDTO implements Serializable {

    private String id;
    private String password;
    private String name;
    private String roleId;
    private String status;
    private String address;
    private String phone;
    private Date createdDate;

    public TblRegistrationDTO() {
    }

    public TblRegistrationDTO(String id, String password, String name, String roleId, String status, String address, String phone, Date createDate) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.createdDate = createDate;
    }

    public TblRegistrationDTO(String id, String password, String name, String roleId) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
