package org.example.data.entitry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "note")
public class NoteEntity extends BaseEntity {

    @Column
    private String title;
    @Column
    private String content;
}