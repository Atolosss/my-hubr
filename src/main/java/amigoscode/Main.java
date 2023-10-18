package amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.mail());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {

        customerRepository.deleteById(id);
    }

    record NewCustomerRequest(String name, String mail, Integer age) {

    }
}

//    @GetMapping("/")
//    public GreetResponce get() {
//        return new GreetResponce("/Hello",
//                List.of("Java", "Golang", "JavaScript"),
//                new Person("Alex",28,30_000));
//    }
//
//    record Person(String name, int age, double savings) {
//
//    }
//
//    record GreetResponce(String greet,
//                         List<String> favProgrammingLanguages,
//                         Person person) {
//    }
//}

//class GreetResponce{
//    private final String great;
//
//    public GreetResponce(String great) {
//        this.great = great;
//    }
//
//    public String getGreat() {
//        return great;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GreetResponce that = (GreetResponce) o;
//        return Objects.equals(great, that.great);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(great);
//    }
//
//    @Override
//    public String toString() {
//        return "GreetResponce{" +
//                "great='" + great + '\'' +
//                '}';
//    }
//}
