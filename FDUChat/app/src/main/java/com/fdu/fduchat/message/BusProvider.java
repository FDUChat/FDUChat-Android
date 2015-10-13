package com.fdu.fduchat.message;

public class BusProvider {
    private static ExBus bus = new ExBus();
    public static ExBus getBus() {
        return bus;
    }
}
