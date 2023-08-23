package com.betrybe.alexandria.controller;

import com.betrybe.alexandria.controller.dto.PublisherDto;
import com.betrybe.alexandria.controller.dto.ResponseDTO;

import com.betrybe.alexandria.models.entities.Publisher;
import com.betrybe.alexandria.service.PublisherService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PublisherController {

  private PublisherService publisherService;

  @Autowired
  public PublisherController(PublisherService publisherService) {
    this.publisherService = publisherService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Publisher>> createPublisher(
      @RequestBody PublisherDto publisherDto) {
    Publisher newPublisher = publisherService.insertPublisher(publisherDto.toPublisher());
    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("Publisher criado com sucesso!",
        newPublisher);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDTO<Publisher>> updatePublisher(@PathVariable Long id,
      @RequestBody PublisherDto publisherDto) {
    Optional<Publisher> optionalPublisher = publisherService.updatePublisher(id,
        publisherDto.toPublisher());

    if (optionalPublisher.isEmpty()) {
      ResponseDTO<Publisher> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o Publisher de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>(
        "Publisher atualizada com sucesso!", optionalPublisher.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Publisher>> removePublisherById(@PathVariable Long id) {
    Optional<Publisher> optionalPublisher = publisherService.removePublisherById(id);

    if (optionalPublisher.isEmpty()) {
      ResponseDTO<Publisher> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o livro de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("publisher removida com sucesso!",
        null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Publisher>> getPublisherById(@PathVariable Long id) {
    Optional<Publisher> optionalPublisher = publisherService.getPublisherById(id);

    if (optionalPublisher.isEmpty()) {
      ResponseDTO<Publisher> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a publisher de ID %d", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Publisher> responseDTO = new ResponseDTO<>("publisher encontrado com sucesso!",
        optionalPublisher.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<PublisherDto> getAllPublishers() {
    List<Publisher> allPublishers = publisherService.getAllPublishers();
    return allPublishers.stream()
        .map((publisher) -> new PublisherDto(publisher.getId(), publisher.getName(),
            publisher.getAddress()))
        .collect(Collectors.toList());
  }
}
