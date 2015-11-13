package es.io.wachsam.repositories;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import es.io.wachsam.model.AlertES;



public interface AlertaRepository extends ElasticsearchRepository<AlertES,String> {
  
}
