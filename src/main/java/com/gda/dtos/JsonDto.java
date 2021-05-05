package com.gda.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gda.utils.JsonFormatter;

public class JsonDto {
    public String format() throws JsonProcessingException {
        return JsonFormatter.format(this);
    }
}
