package com.example.websiteforse.dtos;

public class PostTypeDTO {
    private int id;
    private String typeName;

    public PostTypeDTO(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public PostTypeDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
