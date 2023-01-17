package ru.mrartur.hexmc.packet;

public enum PacketDirection {
    TO_CLIENT(true, false),
    TO_SERVER(false, true),
    BOTH(true, true);
    private final boolean clientBound;
    private final boolean serverBound;

    PacketDirection(boolean clientBound, boolean serverBound) {
        this.clientBound = clientBound;
        this.serverBound = serverBound;
    }

    public boolean isClientBound() {
        return this.clientBound;
    }

    public boolean isServerBound() {
        return this.serverBound;
    }
}
