package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private Map<<K>,List<V>> deathnote = new HashMap<>();
    private long timer;
    private static final long CAUSE_TIMER = 40; 

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRule(int ruleNumber) {
        Objects.requireNonNull(ruleNumber, "number must not be null");
        if((ruleNumber < 1) ||(ruleNumber > RULES.size())){
            throw new IllegalArgumentException("wrong rule number");
        }
        return RULES.get(ruleNumber-1);
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(deathnote.isEmpty() || cause.equals(null)){
            throw new IllegalStateException("invalid deathnote or cause");
        }
        if((System.currentTimeMillis() - timer) < CAUSE_TIMER){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void writeName(String name) {
        if(name.equals(null)){
            throw new NullPointerException("name null");
        }
        deathnote.put(name, new ArrayList<>());
        this.timer = System.currentTimeMillis();
    }

    
}