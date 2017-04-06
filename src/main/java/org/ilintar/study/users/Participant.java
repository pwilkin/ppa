package org.ilintar.study.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pwilkin on 30-Mar-17.
 */
@Entity
@Table(name = "PARTICIPANT")
@NamedQueries(
    @NamedQuery(name = "participantByLiving",
        query = "from Participant where living=:living")
)
public class Participant {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "AGE")
    protected Integer age;

    @Column(name = "EDUCATION")
    protected String education;

    @Column(name = "GENDER")
    protected String gender;

    @Column(name = "LIVING")
    protected String living;

    @OneToMany(targetEntity = Answer.class, mappedBy = "participant", cascade = CascadeType.ALL)
    protected List<Answer> answers = new ArrayList<>();

    @ManyToMany(targetEntity = Group.class, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_PARTICIPANTS", joinColumns = @JoinColumn(name="participant"), inverseJoinColumns = @JoinColumn(name = "grp"))
    protected Set<Group> groups = new HashSet<>();

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", age=" + age +
                ", education='" + education + '\'' +
                ", gender='" + gender + '\'' +
                ", living='" + living + '\'' +
                ", answers=" + answers +
                ", groups=" + groups +
                '}';
    }
}
