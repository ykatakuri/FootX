package com.ykatakuri.footx.model;

public class Field {
    private String imageName;
    private String fieldName;
    private String description;
    private String address;

    public Field(String imageName, String fieldName, String description, String address) {
        this.imageName= imageName;
        this.fieldName= fieldName;
        this.description= description;
        this.address = address;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString()  {
        return this.fieldName+" (Adresse: "+ this.address+")";
    }
}
