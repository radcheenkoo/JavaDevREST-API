package org.example.service.service;

import org.example.service.dto.NoteDto;
import org.example.service.exception.NoteNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<NoteDto> listAll();

    NoteDto add(NoteDto note);

    boolean  deleteById(UUID id) throws NoteNotFoundException;

    boolean  update(UUID id, NoteDto note) throws NoteNotFoundException;

    NoteDto getById(UUID id) throws NoteNotFoundException;

}
