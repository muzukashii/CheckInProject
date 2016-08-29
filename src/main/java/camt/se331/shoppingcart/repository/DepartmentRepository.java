package camt.se331.shoppingcart.repository;
import camt.se331.shoppingcart.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bitee on 6/14/2016.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long>{
}
