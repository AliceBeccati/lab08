package it.unibo.deathnote.impl;

import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sun.source.tree.CaseTree;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation<K,V> implements DeathNote{

    private Map<String,LastDeath> deathnote = new HashMap<>();
    private long timer;
    private String lastNameWritten;
    private static final long CAUSE_TIMER = 40; 
    private static final long DETAILS_TIMER = 6040; 

    @Override
    public String getDeathCause(String name) {
        if(this.deathnote.containsKey(name)){
            String cause = deathnote.get(name).getLastDeathCause();
            if(cause == null){
                return "heart attack";
            }
            return cause;
        }
        else{
            throw new IllegalArgumentException("inexistent name");
        }
    }

    @Override
    public String getDeathDetails(String name) {
        if(this.deathnote.containsKey(name)){
            String detail = deathnote.get(name).getLastDeathDetail();
            if(detail == null){
                return "";
            }
            return detail;
        }
        else{
            throw new IllegalArgumentException("inexistent name");
        }
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
        if(this.deathnote.containsKey(name)){
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(deathnote.isEmpty() || cause.equals(null)){
            throw new IllegalStateException("invalid deathnote or cause");
        }
        if((System.currentTimeMillis() - this.timer) < CAUSE_TIMER){
            deathnote.get(this.lastNameWritten).setLastDeathCause(cause);
            this.timer = System.currentTimeMillis();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if(deathnote.isEmpty() || details.equals(null)){
            throw new IllegalStateException("invalid deathnote or details");
        }
        if((System.currentTimeMillis() - this.timer) < DETAILS_TIMER){
            deathnote.get(this.lastNameWritten).setLastDeathDetail(details);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void writeName(String name) {
        if(name.equals(null)){
            throw new NullPointerException("name null");
        }
        this.deathnote.put(name, new LastDeath());
        this.lastNameWritten = name;
        this.timer = System.currentTimeMillis();
    }

    //fare un inner classe per fare degli oggetti ultimo morto in modo da inserire in tabella direttamente il morto con le sue informazioni relative
    public class LastDeath{
        private String lastDeathCause;
        private String lastDeathDetail;

        public LastDeath(){
            this.lastDeathCause = null;
            this.lastDeathDetail = null;
        }
        public String getLastDeathCause() {
            return lastDeathCause;
        }
        public void setLastDeathCause(String lastDeathCause) {
            this.lastDeathCause = lastDeathCause;
        }
        public String getLastDeathDetail() {
            return lastDeathDetail;
        }
        public void setLastDeathDetail(String lastDeathDetail) {
            this.lastDeathDetail = lastDeathDetail;
        }
        
    }
}