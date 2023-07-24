package com.example.meteorCleaning.service;

import com.example.meteorCleaning.model.EstimateOrder;
import com.example.meteorCleaning.model.OrderPrices;
import com.example.meteorCleaning.repository.datajpa.DataJpaOrderRepository;
import com.example.meteorCleaning.repository.datajpa.DataJpaPriceRepository;
import com.example.meteorCleaning.util.exception.IllegalRequestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Service
public class EstimateDataService {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    DataJpaOrderRepository repository;
    @Autowired
    DataJpaPriceRepository priceRepository;
    @Autowired
    private JavaMailSender mailSender;

    public List<EstimateOrder> getAll() {
        return repository.getAll();
    }

    public Page<EstimateOrder> findPage(int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return repository.getAll(pageable);
    }

    public void sendEmail(String[] to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("info@meteorcleaning.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }

    public EstimateOrder get(int id) {
        return repository.get(id);
    }

    public EstimateOrder save(EstimateOrder order) {
        if (!validatePriceIntegrity(order)) {
            throw new IllegalRequestDataException("Price integrity validation failed");
        }
        return repository.save(order);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public OrderPrices getPrices() {
        return priceRepository.getPrices();
    }

    public boolean validatePriceIntegrity(EstimateOrder order) {
        double count = 0;
        OrderPrices prices = getPrices();
        int houseType = Integer.parseInt(order.getHousingType());
        int squareFtCount = order.getSquareFt() != null ? Integer.parseInt(order.getSquareFt()) : 0;
        switch (houseType) {
            case 0 -> count += prices.getStudio();
            case 1 -> count += prices.getApartments();
            case 2 -> count += countHouseBaseByFt(squareFtCount, prices.getHouseFt(), prices.getHouse());
            case 3 -> count += prices.getOffice() * squareFtCount;
        }

        count += prices.getBedroom() * Integer.parseInt(order.getBedrooms());

        count += prices.getBathroom() * Integer.parseInt(order.getBathrooms());

        count += ((double) prices.getBathroom() / 2) * Integer.parseInt(order.getHalfBathrooms());

        if (order.getGreenClean()) {
            count += prices.getGreenClean();
        }
        if (order.getDeepClean()) {
            count = Math.round(count * prices.getDeepClean());
        }

        if (order.getMicrowaveClean()) {
            count += prices.getMicrowaveClean();
        }
        if (order.getRefrigeratorClean()) {
            count += prices.getRefrigeratorClean();
        }
        if (order.getOvenClean()) {
            count += prices.getOvenClean();
        }

        if (order.getWindowClean() > 0) {
            count += prices.getWindows() * order.getWindowClean();
        }

        if (order.getCabinetClean() > 0) {
            count += prices.getCabinet() * order.getCabinetClean();
        }

        if (order.getDishesClean()) {
            count += prices.getDishesWash();
        }

        //check if selected date is weekend
        count += order.getDateTime().getDayOfWeek().getValue() == 7 ? prices.getWeekend() : 0;

        count = Math.round(count);

        return count == order.getEstimatedPrice();
    }

    private double countHouseBaseByFt(int squareFt, double sqPrice, int housePrice) {
        return squareFt > 1000 ? (squareFt - 1000) * sqPrice : housePrice;
    }

    public OrderPrices savePrices(OrderPrices prices) {
        return priceRepository.save(prices);
    }

    public Map<Object, Long> getAllDates() {
        return repository.getAllDates();
    }

    public EstimateOrder getByPaymentIntentId(String paymentIntentId) {
        return repository.getByPaymentIntentId(paymentIntentId);
    }

    public void update(EstimateOrder order) {
        log.info("update order with id={}", order.id());
        repository.update(order);
    }

    public boolean cancelOrder(String id) {
        log.info("delete order with payment_id={} ", id);
        return repository.deleteByPaymentIntent(id) != 0;
    }
}
