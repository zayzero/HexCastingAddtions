package net.mcsweatshop.hexcastingadditions.mixininterfaces;

public interface AsyncRuns {
    public void incrementAsync();
    public void decrementAsync();
    public int getAsyncCount();
    public boolean shouldCancel();

}
