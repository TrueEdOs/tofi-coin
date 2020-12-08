package org.tofi.coin.node.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tofi.coin.node.Utils;
import org.tofi.coin.node.configuration.State;
import org.tofi.coin.node.models.Block;
import org.tofi.coin.node.models.BlockHeader;
import org.tofi.coin.node.models.Transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

@Service
public class Core {
    private static final Logger LOG = Logger.getLogger("Core");
    private static final String GOD = "GOD";
    private static final int AWARD = 1000000000;
    private static final int NUMBER_OF_TRANSACTS = 5;
    private final Storage storage;
    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();
    private final Map<String, Block> blocks = new ConcurrentHashMap<>();
    private final Map<String, Long> balances = new ConcurrentHashMap<>();
    private final Map<String, Transaction> pendingTransactions = new ConcurrentHashMap<>();
    private State state;

    @Autowired
    public Core() {
        storage = new Storage();
        LOG.info("CORE IS UP");
        init();
    }

    private void init() {
        state = storage.loadState();

        for (Block block : state.getProceededBlocks()) {
            blocks.put(block.getBlockHeader().getHash(), block);
            balances.put(block.getBlockHeader().getAddressOfSolver(), balances.getOrDefault(block.getBlockHeader().getAddressOfSolver(), 0L) + AWARD);

            for (Transaction transaction : block.getTransactions()) {
                String sender = transaction.getSender();
                String receiver = transaction.getReceiver();
                transaction.setBlock(block.getBlockHeader().getNumber());
                long amount = transaction.getAmount();

                transactions.put(transaction.getHash(), transaction);
                balances.put(sender, balances.get(sender) - amount);
                balances.put(receiver, balances.getOrDefault(receiver, 0L) + amount);
                pendingTransactions.remove(transaction.getHash());
            }
        }
    }

    private boolean proceedBlock(final Block block) {
        if (blocks.containsKey(block.getBlockHeader().getHash())) {
            return false;
        }

        BlockHeader blockHeader = block.getBlockHeader();

        if (blockHeader.getTimestamp() > System.currentTimeMillis()) {
            return false;
        }

        if (!state.getProceededBlocks().isEmpty()) {
            Block lastBlock = state.getProceededBlocks().get(state.getProceededBlocks().size() - 1);

            if (blockHeader.getNumber() != lastBlock.getBlockHeader().getNumber() + 1 ||
                    !blockHeader.getPrevBlockHash().equals(lastBlock.getBlockHeader().getHash())) {
                return false;
            }
        }

        for (Transaction transaction : block.getTransactions()) {
            String sender = transaction.getSender();

            if (sender == null) {
                return false;
            }

            if (balances.getOrDefault(sender, 0L) < transaction.getAmount()) {
                return false;
            }
        }

        for (Transaction transaction : block.getTransactions()) {
            String sender = transaction.getSender();

            if (sender == null) {
                return false;
            }

            if (balances.getOrDefault(sender, 0L) < transaction.getAmount()) {
                return false;
            }
        }

        for (Transaction transaction : block.getTransactions()) {
            String sender = transaction.getSender();
            String receiver = transaction.getReceiver();
            transaction.setBlock(blockHeader.getNumber());
            long amount = transaction.getAmount();

            balances.put(sender, balances.get(sender) - amount);
            balances.put(receiver, balances.getOrDefault(receiver, 0L) + amount);
            pendingTransactions.remove(transaction.getHash());
        }

        String solver = block.getBlockHeader().getAddressOfSolver();
        balances.put(solver, balances.getOrDefault(solver, 0L) + AWARD);
        state.getProceededBlocks().add(block);
        blocks.put(blockHeader.getHash(), block);
        state.getProceededBlocks().add(block);

        state.setBlocksProceeded(state.getBlocksProceeded() + 1);
        storage.saveState(state);
        LOG.info("New block proceeded. Block hash" + blockHeader.getHash());
        return true;
    }

    public Block getLastBlock() {
        return state.getProceededBlocks().isEmpty() ? null : state.getProceededBlocks().get(state.getProceededBlocks().size() - 1);
    }

    public long getGoal() {
        Block block = getLastBlock();
        return block == null ? 0 : 1000 - (System.currentTimeMillis() - block.getBlockHeader().getTimestamp()) / 1000;
    }

    public List<Transaction> getTransactionByUser(final String address) {
        List<Transaction> transactionsByUser = new ArrayList<>();

        for (Map.Entry<String, Transaction> entry : transactions.entrySet()) {
            String sender = entry.getValue().getSender();
            String receiver = entry.getValue().getReceiver();

            if (address.equals(sender) || address.equals(receiver)) {
                transactionsByUser.add(entry.getValue());
            }
        }

        return transactionsByUser;
    }

    public long getBalance(final String address) {
        return balances.getOrDefault(address, 0L);
    }
    public Transaction getTransaction(final String hash) {
        return transactions.get(hash);
    }

    public Block getBlock(final String hash) {
        return blocks.get(hash);
    }

    public boolean putBlock(final Block block) {
        return proceedBlock(block);
    }

    public Block getHalfBackedBlock(final String miner) {
        Block newBlock = new Block();
        BlockHeader newBlockHeader = new BlockHeader();
        newBlockHeader.setTimestamp(System.currentTimeMillis());
        newBlock.setBlockHeader(newBlockHeader);
        ArrayList<Transaction> arr = new ArrayList<>();

        for (Map.Entry<String, Transaction> pendingTransaction : pendingTransactions.entrySet()) {
            if (arr.size() < NUMBER_OF_TRANSACTS) {
                arr.add(pendingTransaction.getValue());
            }
        }

        newBlock.setTransactions(new Transaction[arr.size()]);
        String[] ts = new String[arr.size()];

        for (int i = 0; i < ts.length; ++i) {
            newBlock.getTransactions()[i] = arr.get(i);
            ts[i] = newBlock.getTransactions()[i].toString();
        }

        newBlockHeader.setMerkleRootOfTransactions(Utils.getMerkleRoot(ts));
        newBlockHeader.setAddressOfSolver(miner);

        if (!state.getProceededBlocks().isEmpty()) {
            Block lastBlock = state.getProceededBlocks().get(state.getProceededBlocks().size() - 1);
            newBlockHeader.setPrevBlockHash(lastBlock.getBlockHeader().getHash());
            newBlockHeader.setNumber(lastBlock.getBlockHeader().getNumber() + 1);
        } else {
            newBlockHeader.setNumber(0);
        }

        return newBlock;
    }

    public boolean addPendingTransaction(final Transaction transaction) {
        if (transactions.containsKey(transaction.getHash())) {
            LOG.warning("Pending transaction is already proceeded. Tx hash" + transaction.getHash());
            return false;
        }

        transactions.put(transaction.getHash(), transaction);
        pendingTransactions.put(transaction.getHash(), transaction);
        LOG.info("Pending transaction is proceeded. Tx hash" + transaction.getHash());

        return true;
    }

    private static final class Worker extends Thread implements AutoCloseable {

        private static final Logger LOG = Logger.getLogger("Core");
        private boolean active = true;
        private Queue<Runnable> tasks = new ConcurrentLinkedDeque<>();

        public Worker() {
            super("Core");
        }

        public void open() {
            start();
        }

        @Override
        public void close() {
            add(() -> active = false);

            try {
                join();
            } catch (final Throwable e) {
                LOG.warning("Should never happen: " + e);
            }
        }

        @Override
        public void run() {
            while (active) {
                try {
                    final Runnable command = tasks.remove();
                    command.run();
                } catch (final Throwable e) {
                    LOG.warning("Withdrawal Engine can't handle command: " + e);
                }
            }
        }

        public void add(final Runnable command) {
            try {
                tasks.add(command);
            } catch (final Throwable e) {
                LOG.warning("Withdrawal Engine can't put command to queue: " + e);
            }
        }

    }

}
