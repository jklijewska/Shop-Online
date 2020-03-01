package pl.jklijewska.shop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.jklijewska.shop.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("PLUS")
public class ShopPlusService extends ShopStartService {

    @Value("${shop-plus.tax}")
    private BigDecimal tax;

    List<Product> taxList = new ArrayList<>();

    public List<Product> addTax() {

        taxList = super.allProducts().stream()
                .map(f -> new Product(f.getName(), (f.getPrice().multiply(tax)).add(f.getPrice())))
                .collect(Collectors.toList());
        return taxList;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sumWithTax() {
        addTax();
        super.sum(taxList);
    }
}
