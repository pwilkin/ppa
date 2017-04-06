package org.ilintar.study.users;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pwilkin on 30-Mar-17.
 */
@Entity
@Table(name = "GROUPS")
public class Group {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "NAME")
    protected String name;

    @ManyToMany(targetEntity = Participant.class, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_PARTICIPANTS", joinColumns = @JoinColumn(name="grp"), inverseJoinColumns = @JoinColumn(name = "participant"))
    protected Set<Participant> participants = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
