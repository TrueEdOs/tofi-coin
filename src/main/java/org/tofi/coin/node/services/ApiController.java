package org.tofi.coin.node.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tofi.coin.node.dto.PushedResponse;
import org.tofi.coin.node.models.Block;
import org.tofi.coin.node.models.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController()
@RequestMapping("/api")

public class ApiController {
    private final Core core;

    @Autowired
    public ApiController(final Core core) {
        this.core = core;
    }

    // MODERN BROWSERS ARE SUPER GAY
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    @ResponseBody
    public long getBalance(@RequestParam final String address) {
        return core.getBalance(address);
    }

    @RequestMapping(value = "/goal", method = RequestMethod.GET)
    @ResponseBody
    public long getGoal() {
        return core.getGoal();
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    @ResponseBody
    public Transaction getTransaction(@RequestParam final String hash) {
        return core.getTransaction(hash);
    }

    @RequestMapping(value = "/transaction/user", method = RequestMethod.GET)
    @ResponseBody
    public List<Transaction> getTransactionsByUser(@RequestParam final String address) {
        return core.getTransactionByUser(address);
    }

    @RequestMapping(value = "/block", method = RequestMethod.GET)
    @ResponseBody
    public Block getBlock(@RequestParam final String hash) {
        return core.getBlock(hash);
    }

    @RequestMapping(value = "/block/prepared", method = RequestMethod.GET)
    @ResponseBody
    Block getHalfBackedBlock(@RequestParam final String miner) {
        return core.getHalfBackedBlock(miner);
    }


    @RequestMapping(value = "/block/push", method = RequestMethod.POST)
    @ResponseBody
    PushedResponse pushBlock(@RequestBody final Block block) {
        return new PushedResponse().setPushed(core.putBlock(block));
    }

    @RequestMapping(value = "/transaction/push", method = RequestMethod.POST)
    @ResponseBody
    PushedResponse pushTransaction(@RequestBody final Transaction transaction) {
        return new PushedResponse().setPushed(core.addPendingTransaction(transaction));
    }
}
