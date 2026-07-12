package it.unicam.cs.mpgc.rpg125656.entity;

public class Boss extends Enemy {

    public Boss(String name, int maxAuthority) {
        super(name, maxAuthority, true);
    }

    public Boss(String name, int maxAuthority, int authority, int irritation) {
        super(name, maxAuthority, authority, irritation, true);
    }
}