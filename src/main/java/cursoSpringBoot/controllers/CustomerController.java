package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class CustomerController {
    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
          new Customer(123, "Gerardo López", "gerardol", "pass1"),
          new Customer(345, "Elmer Garcia", "elmerg", "pass2"),
          new Customer(567, "Laura Sanchez", "lauras", "pass3"),
          new Customer(789, "Carlos Martínez", "carlosm", "pass4")
    ));

    //@GetMapping("/clients")
    @GetMapping()
    //@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>>  getCustomers(){
        return ResponseEntity.ok(customers);
    }

    //@GetMapping("/clients/{userName}")
    @GetMapping("/{id}")
    //@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable int id){
        for (Customer customer: customers) {
            if (customer.getId() == id){
                return ResponseEntity.ok(customer);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el id: " + id);
    }

    //@PostMapping("/clients")
    @PostMapping()
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postClient(@RequestBody Customer customer){
        customers.add(customer);
        //return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente: " + customer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getUserName())
                .toUri();
        //return ResponseEntity.created(location).build();
        return ResponseEntity.created(location).body(customer);
    }

    //@PutMapping("/clients")
    @PutMapping()
    //@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> putClient(@RequestBody Customer customer){
        for (Customer customer1 : customers) {
            if (customer1.getId() == customer.getId()){
                customer1.setName(customer.getName());
                customer1.setUserName(customer.getUserName());
                customer1.setPassword(customer.getPassword());
                //return ResponseEntity.ok("Cliente modificado satisfactoriamente: " + customer.getId());
                return ResponseEntity.noContent().build();
            }
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + customer.getId());
        return ResponseEntity.noContent().build();
    }

    //@DeleteMapping("/clients/{id}")
    @DeleteMapping("/{id}")
    //@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable int id){
        for (Customer customer: customers) {
            if (customer.getId() == id) {
                customers.remove(customer);
                //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eliminado satisfactoriamente: " + id);
                return ResponseEntity.noContent().build();
            }
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el: " + id);
        return ResponseEntity.noContent().build();
    }

    //@PatchMapping("/clients")
    //@PatchMapping()
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> patchClient(@RequestBody Customer customer){
        for (Customer c: customers) {
            if (c.getId() == customer.getId()){
                if (customer.getName() != null) {
                    c.setName(customer.getName());
                }
                if (customer.getUserName() != null) {
                    customer.setUserName(customer.getUserName());
                }
                if (customer.getPassword() != null) {
                    customer.setPassword(customer.getPassword());
                }
                return ResponseEntity.ok("Cliente modificado satisfactoriamente: " + customer.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con el ID: " + customer.getId());
    }
}
