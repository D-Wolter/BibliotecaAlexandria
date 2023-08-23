package com.betrybe.alexandria.controller;

import com.betrybe.alexandria.controller.dto.AuthorDTO;

import com.betrybe.alexandria.controller.dto.ResponseDTO;
import com.betrybe.alexandria.models.entities.Author;

import com.betrybe.alexandria.service.AuthorService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  private AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Author>> createAuthor(@RequestBody AuthorDTO authorDTO) {
    Author newAuthor = authorService.insertAuthor(authorDTO.toAuthor());
    ResponseDTO<Author> responseDTO = new ResponseDTO<>("Pessoa Autora com sucesso!", newAuthor);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDTO<Author>> updateAuthor(@PathVariable Long id,
      @RequestBody AuthorDTO authorDTO) {
    Optional<Author> optionalAuthor = authorService.updateAuthor(id, authorDTO.toAuthor());

    if (optionalAuthor.isEmpty()) {
      ResponseDTO<Author> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o author de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Author> responseDTO = new ResponseDTO<>(
        "Pessoa Autora atualizada com sucesso!", optionalAuthor.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Author>> removeAuthorById(@PathVariable Long id) {
    Optional<Author> optionalAuthor = authorService.removeAuthorById(id);

    if (optionalAuthor.isEmpty()) {
      ResponseDTO<Author> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o livro de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Author> responseDTO = new ResponseDTO<>("Pessoa Autora removida com sucesso!",
        null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Author>> getAuthorById(@PathVariable Long id) {
    Optional<Author> optionalAuthor = authorService.getAuthorById(id);

    if (optionalAuthor.isEmpty()) {
      ResponseDTO<Author> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a pessoa autora de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Author> responseDTO = new ResponseDTO<>("Livro encontrado com sucesso!",
        optionalAuthor.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<AuthorDTO> getAllAuthors() {
    List<Author> allAuthors = authorService.getAllAuthors();
    return allAuthors.stream()
        .map((author) -> new AuthorDTO(author.getId(), author.getName(), author.getNationality()))
        .collect(Collectors.toList());
  }
}
