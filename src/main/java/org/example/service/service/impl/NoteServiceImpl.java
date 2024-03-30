package org.example.service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.data.entitry.NoteEntity;
import org.example.data.repository.NoteRepository;
import org.example.service.dto.NoteDto;
import org.example.service.exception.NoteNotFoundException;
import org.example.service.mapper.NoteMapper;
import org.example.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired private NoteRepository noteRepository;
    @Autowired private NoteMapper noteMapper;

    @Override
    public List<NoteDto> listAll() {
        return noteMapper.toNoteDtos(noteRepository.findAll());
    }

    @Override
    @Transactional
    public NoteDto add(NoteDto note) {
        NoteEntity entity = noteMapper.toNoteEntity(note);

        entity.setId(null);
        entity.setCreatedDate(LocalDate.now());
        entity.setLastUpdatedDate(LocalDate.now());

        return noteMapper.toNoteDto(noteRepository.save(entity));
    }

    @Override
    @Transactional
    public boolean  deleteById(UUID id) throws NoteNotFoundException {
        NoteDto note = getById(id);
        if (Objects.isNull(note.getId())) {
            log.info("Error ==== >>> " + new NoteNotFoundException(id));
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean  update(UUID id, NoteDto note) throws NoteNotFoundException {

        if (Objects.isNull(note.getId())) {
            log.info("Error: " + new NoteNotFoundException());
            return false;
        }

        NoteDto resNoteDto = getById(id);

        resNoteDto.setTitle(note.getTitle());
        resNoteDto.setContent(note.getContent());
        resNoteDto.setLastUpdatedDate(LocalDate.now());

        noteRepository.save(noteMapper.toNoteEntity(resNoteDto));
        return true;
    }

    @Override
    public NoteDto getById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return noteMapper.toNoteDto(optionalNote.get());
        } else {
            throw new NoteNotFoundException(id);
        }
    }



}
