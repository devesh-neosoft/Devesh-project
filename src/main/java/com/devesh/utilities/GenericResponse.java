package com.devesh.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T>{

    public String message;
    public boolean success;
    public int code;
    public T data;
}
