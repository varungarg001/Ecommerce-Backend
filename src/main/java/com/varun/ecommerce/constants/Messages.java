package com.varun.ecommerce.constants;

public enum Messages {

    SUCCESS("success");

    final String value;

    Messages(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
