package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column @NonNull
    private String title;

    @Column @NonNull @Size(max = 1500)
    private String description;

    @Column
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"jobsCreated","jobApplied"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userMadeBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"jobApplied","jobsCreated"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> usersApplied = new HashSet<>();

    @ManyToMany(mappedBy="recommendedJobs",fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"recommendedJobs","jobsCreated","jobApplied","interestReactions"},allowSetters = true)
    @Fetch(value= FetchMode.SELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> recommendedTo = new HashSet<User>();
}
