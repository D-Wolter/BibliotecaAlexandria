package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.entities.Publisher;

public record PublisherDto(Long id, String name, String address) {

  public Publisher toPublisher() {
    return new Publisher(id, name, address);
  }
}
