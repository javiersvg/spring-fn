package functions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.stream.config.StreamListeningFunctionInvoker;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import reactor.core.publisher.Flux;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.class})
public class AppTest {
    
    @Autowired
    private StreamListeningFunctionInvoker invoker;

    @Test
    public void function() {
        //Given
        ItemList data = new ItemList();
        Item item1 = new Item();
        Item item2 = new Item();
        data.setItems(new Item[]{item1, item2});
        //When
        Flux<Item> flux = new App().function().apply(Flux.just(data));
        //Then
        flux.count().subscribe(c -> Assert.assertEquals(new Long(2), c));
    }

    @Test
    public void processor() {
        //Given
        ItemList data = new ItemList();
        Item item1 = new Item();
        Item item2 = new Item();
        data.setItems(new Item[]{item1, item2});
        //When
        Flux<Message<?>> flux = invoker.handle(Flux.just(MessageBuilder.withPayload(data).build()));
        //Then
        flux.count().subscribe(c -> Assert.assertEquals(new Long(2), c));
    }
}
