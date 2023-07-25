package com.craftaro.ultimatetools.enchant;

import java.lang.reflect.Method;

public class HandlerWrapper {

    private final AbstractEnchant enchant;
    private final Method handler;

    public HandlerWrapper(AbstractEnchant enchant, Method handler) {
        this.enchant = enchant;
        this.handler = handler;
    }

    public AbstractEnchant getEnchant() {
        return enchant;
    }

    public Method getHandler() {
        return handler;
    }
}
