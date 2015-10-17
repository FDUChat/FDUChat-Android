package com.fdu.fduchat.backend;

public class CoreProvider {
    private static Core core = new Core();
    public static Core getCoreInstance() {
        return core;
    }
}
