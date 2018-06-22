package functions;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionScan;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;

@SpringBootApplication
@FunctionScan
public class App {
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    @Bean
    public Function<Flux<ItemList>, Flux<Item>> function() {
        return f -> f.flatMap(l -> Flux.fromArray(l.getItems()));
    }
}

class ItemList {
    private Item[] items;
    
    public Item[] getItems() {
        return this.items;
    }
    
    public void setItems(Item[] items) {
        this.items = items;
    }
}

class Item {}