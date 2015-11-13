package es.io.wachsam.repositories;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import es.io.wachsam.model.Peligro;

public interface PeligroRepository extends ElasticsearchRepository<Peligro,String> {
  
}
