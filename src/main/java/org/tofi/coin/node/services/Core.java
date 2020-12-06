package org.tofi.coin.node.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tofi.coin.node.configuration.State;
import org.tofi.coin.node.models.Block;
import org.tofi.coin.node.models.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

@Service
public class Core {
    private static final Logger LOG = Logger.getLogger("Core");

    private final Storage storage;
    private Map<String, Transaction> transactions = new HashMap<>();
    private Map<String, Block> blocks = new HashMap<>();
    private Map<String, Transaction> pendingTransaction;
    private State state;

    @Autowired
    public Core() {
        storage = new Storage();
        state = storage.loadState();
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
