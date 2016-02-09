package models.user;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by aivlev on 1/27/16.
 */
@Entity
@Table(name="app_profile")
public class ApplicationProfile extends Model {

    @Id
    @Column(name="id")
    private  Long id;

    @Column(name="email")
    private String email;

    @Column(name="active")
    private int active;

    public ApplicationProfile() {

    }

    public ApplicationProfile(String email, int active) {
        this.email = email;
        this.active = active;
    }

    public static Finder<Long,ApplicationProfile> find = new Finder<>(
            Long.class, ApplicationProfile.class
    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ApplicationProfile{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
