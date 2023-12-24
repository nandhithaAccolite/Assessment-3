package com.accolite.Payment.service;

import com.accolite.Payment.exception.OfflinePaymentLimitExceeded;
import com.accolite.Payment.model.User;
import com.accolite.Payment.model.Wallet;
import com.accolite.Payment.repository.UserRepository;
import com.accolite.Payment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private WalletRepository walletRepository;

    public void addFunds(User user, BigDecimal amount) {
        Wallet wallet = user.getWallet();
        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }
    @Override
    public void addOfflinePaymentAmount(User user, BigDecimal amount) {
        Wallet wallet = user.getWallet();
        BigDecimal newBalance = wallet.getOfflineBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.valueOf(5000)) <= 0) {
            wallet.setOfflineBalance(newBalance);
/*            wallet.setBalance(wallet.getBalance().subtract(newBalance));*/
        } else {
            throw new OfflinePaymentLimitExceeded("Offline limit only 5000");
        }
    }

    public List<String> generateOfflinePaymentCodes(User user, int numberOfCodes) {
        Wallet wallet = user.getWallet();
        BigDecimal balance = wallet.getBalance();
        List<String> generatedCodes = new ArrayList<>();
        if (user.isApproved() && balance.compareTo(BigDecimal.ZERO) > 0)
        {

            for (int i = 0; i < numberOfCodes; i++)
            {
                String code = generateUniqueCode();
                generatedCodes.add(code);

            }
        }
        else
        {
            throw new RuntimeException("Insufficient funds for generating offline payment codes");
        }
        return generatedCodes;
    }

    @Override
    public User getUserFromToken(String token) {
        String userId = userService.getUserIdFromToken(token);

        // Assuming you have a method to fetch user by userId from a user repository
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user;
    }

    @Override
    public BigDecimal getWalletBalance(User user) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found for the user"));

        return wallet.getBalance();
    }

    private static String generateUniqueCode() {
        // Generate a UUID and remove hyphens to create a unique code
        String code = UUID.randomUUID().toString().replace("-", "");
        return code;
    }
}
