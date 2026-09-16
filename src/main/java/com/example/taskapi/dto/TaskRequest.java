package com.example.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequest {

    @NotBlank(message = "The title is mandatory.")
    @Size(max = 100, message = "The title must have a maximum of 100 characters.")
    private String title;

    @NotBlank(message = "The description is mandatory.")
    @Size(max = 500, message = "The description must have a maximum of 500 characters.")
    private String description;

    public TaskRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
