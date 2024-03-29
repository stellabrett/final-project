package Cooking.School.Project.cookingSchool.entities;

import Cooking.School.Project.cookingSchool.security.AUTHORITIES;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "BENUTZER")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "userSequence")
    @GenericGenerator(
            name = "userSequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )

    private Long userId;

    @Setter
    private String firstname;

    @Setter
    private String lastname;

    @Setter
    private String address;

    @Setter
    private String mobile;

    @Setter
    private String email;

    // @JsonIgnore
    @Setter
    private String password;

    @Column(name = "USERNAME")
    @Setter
    private String username;

    @Setter
    @JsonProperty //springboot streicht is von isAdmin und macht sonst admin daraus, kA warum!
    private boolean isAdmin;

    // last booked Course
    @Setter
    private Long finishedCourses;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    Set<GrantedAuthorityImpl> authorities;

    @JsonIgnore
    @Setter
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "courseId"))
    private Set<Course> courses = new HashSet<>();


    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    //APPUSER für jeden benutzer, wenn isAdmin true ist wir rolle Amin hinzugefügt wenn false nur user
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("get_authorities");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AUTHORITIES.APPUSER.name()));

        System.out.println("isAdmin: " + isAdmin);
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority(AUTHORITIES.ADMIN.name()));
        }

        System.out.println(authorities);
        return authorities;
    }
}
