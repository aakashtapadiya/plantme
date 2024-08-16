package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.CartItem;
import com.ecommerce.plantme.entity.Payment;
import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.CartDTO;
import com.ecommerce.plantme.payloads.PaymentDTO;
import com.ecommerce.plantme.repository.CartRepo;
import com.ecommerce.plantme.repository.PaymentRepo;
import com.ecommerce.plantme.repository.UserRepo;
import com.ecommerce.plantme.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private ModelMapper modelMapper;
    private UserRepo userRepo;
    private CartRepo cartRepo;


    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo,ModelMapper modelMapper,UserRepo userRepo,CartRepo cartRepo) {
        this.paymentRepo = paymentRepo;
        this.modelMapper = modelMapper;
        this.userRepo=userRepo;
        this.cartRepo=cartRepo;
    }
    @Override
    public PaymentDTO processPayment(Long userId, String paymentMethod) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        List<CartItem> cartItems=cartRepo.findByUserId(userId);
        Double Amount=0.0;
        for (CartItem c:cartItems){
            Amount+=c.getPrice();
            //change amount if there is any discount on particular product
        }
        System.out.println("set the payment");
        //TOdo:Apply bank offer ,etc on total amount
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus("SUCCESS");
        payment.setAmount(Amount);
        paymentRepo.save(payment);
        return this.paymentToDto(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepo.findPaymentsByUserId(userId);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("Payments", "payment", userId);
        }
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment:payments) {
            paymentDTOS.add(this.paymentToDto(payment));
        }
        return paymentDTOS;
    }

    /**
     * MAPPING PaymentDTO to Product
     * @param PaymentDTO
     * @return Payment
     */
    private Payment dtoToPayment (PaymentDTO PaymentDTO) {
        Payment payment = this.modelMapper.map(PaymentDTO, Payment.class);
        return payment;
    }

    /**
     * MAPPING Product TO PaymentDTO
     * @param payment
     * @return PaymentDTO
     */
    private PaymentDTO paymentToDto(Payment payment) {
        PaymentDTO PaymentDTO = this.modelMapper.map(payment, PaymentDTO.class);
        return PaymentDTO;
    }

}
