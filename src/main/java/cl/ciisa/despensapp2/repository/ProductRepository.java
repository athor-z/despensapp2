package cl.ciisa.despensapp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ciisa.despensapp2.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}