package pl.jklijewska.shop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.jklijewska.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("PRO")
public class ShopProService extends ShopPlusService {

    @Value("${shop-pro.discount}")
    private BigDecimal discount;

    @EventListener(ApplicationReadyEvent.class)
    void addDiscount() {

        List<Product> discountList = super.addTax().stream()
                .map(f -> new Product(f.getName(), (f.getPrice()).subtract(f.getPrice().multiply(discount))))
                .collect(Collectors.toList());

        super.sum(discountList);
    }

}
