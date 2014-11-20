package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 事件类型
 */
@Entity
@Table(name = "eventType", schema = "dbo")
public class EventType implements java.io.Serializable {

    private static final long serialVersionUID = -5333494794989165836L;

    private long typeId;
    private String name;
    private String description;
    private String cssClass;

    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Set<Event> eventses = new HashSet<Event>(0);

    public EventType() {
    }

    public EventType(long typeId) {
        this.typeId = typeId;
    }

    public EventType(long typeId, String name, String description,
                     String cssClass, Set<Event> eventses) {
        this.typeId = typeId;
        this.name = name;
        this.description = description;
        this.cssClass = cssClass;
        this.eventses = eventses;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeId", unique = true, nullable = false)
    public long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 250)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "cssClass", length = 20)
    public String getCssClass() {
        return this.cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventType")
    public Set<Event> getEventses() {
        return this.eventses;
    }

    public void setEventses(Set<Event> eventses) {
        this.eventses = eventses;
    }

}
