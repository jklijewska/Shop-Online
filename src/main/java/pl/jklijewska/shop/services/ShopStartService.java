package pl.jklijewska.shop.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.jklijewska.shop.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("START")
public class ShopStartService {

    final static BigDecimal MIN = new BigDecimal("50");

    final static BigDecimal MAX = new BigDecimal("300");

    private List<Product> basketList = new ArrayList<>();

    public ShopStartService() {

        for (int i = 0; i < 4; i++) {
            double tmp = Math.random();
            BigDecimal randomBigDecimal = MIN.add(new BigDecimal(tmp).multiply(MAX.subtract(MIN)));
            BigDecimal price = randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            basketList.add(new Product("product" + i, price));
        }
    }

    public List<Product> allProducts() {
        return basketList;
    }

    public BigDecimal sum(List<Product> list) {
        allProducts();
        BigDecimal sum = list
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .get();
        list.forEach(System.out::println);
        System.out.println("All products price: " + sum + " $");
        return sum;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showAll() {
        // allProducts();
        sum(basketList);
    }
}

