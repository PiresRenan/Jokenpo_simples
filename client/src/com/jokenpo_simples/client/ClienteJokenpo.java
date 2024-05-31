package com.jokenpo_simples.client;

import com.jokenpo_simples.client.controller.JokenpoController;
import com.jokenpo_simples.client.view.JokenpoView;

public class ClienteJokenpo {
    public static void main(String[] args) {
        JokenpoView view = new JokenpoView();
        JokenpoController controller = new JokenpoController(view);
        controller.iniciar();
    }
}