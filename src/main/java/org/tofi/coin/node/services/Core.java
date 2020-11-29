package org.tofi.coin.node.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Core {
    private Verifier verifier;
    private ApiController apiController;

    @Autowired
    public Core(final Verifier verifier, final ApiController apiController) {
        this.verifier = verifier;
        this.apiController = apiController;
    }
}
