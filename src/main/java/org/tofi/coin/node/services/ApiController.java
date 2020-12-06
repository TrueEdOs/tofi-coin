package org.tofi.coin.node.services;

import org.springframework.web.bind.annotation.*;
import org.tofi.coin.node.models.Transaction;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    @ResponseBody
    Transaction getTransaction(@RequestParam final String hash) {
        if (!"KEK".equals(hash)) {
            return null;
        }
        
        Transaction transaction = new Transaction();
        transaction.setAmount(1289471293);
        transaction.setPublicKey("NAHUI IDI");
        transaction.setSender("VANUA PIDOR");
        transaction.setSignature("KOSTYA PIDOR");
        transaction.setFee(666);
        return transaction;
    }
}
