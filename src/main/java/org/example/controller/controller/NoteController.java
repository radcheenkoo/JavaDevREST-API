package org.example.controller.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.controller.request.NoteRequest;
import org.example.data.entitry.NoteEntity;
import org.example.service.dto.NoteDto;
import org.example.service.exception.NoteNotFoundException;
import org.example.service.mapper.NoteMapper;
import org.example.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Validated
@Controller
@RequestMapping("/main/note")
public class NoteController {

    @Autowired private NoteService noteService;
    @Autowired private NoteMapper noteMapper;

    @GetMapping("/list")
    public ResponseEntity<List<NoteDto>> noteList() {

        final List<NoteDto> dtos = noteService.listAll();

        return dtos != null && !dtos.isEmpty()
                ? new ResponseEntity<>(dtos, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/create")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {

        noteMapper.toNoteEntity(noteService.add(noteDto));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<NoteDto> updateNote(
            @NotEmpty @PathVariable(value="id") String id,
            @RequestBody NoteDto noteDto) throws NoteNotFoundException {

        noteDto.setId(UUID.fromString(id));

        final boolean isUpdated = noteService.update(noteDto.getId(), noteDto);

        return isUpdated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<NoteDto> deleteNoteById(@Valid @NotNull @PathVariable(value="id") String id) throws NoteNotFoundException {
        final  boolean isDeleted = noteService.deleteById(UUID.fromString(id));

        return isDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
