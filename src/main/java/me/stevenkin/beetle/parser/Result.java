package me.stevenkin.beetle.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.stevenkin.beetle.request.Request;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Object item;
    private List<Request> requests;
}
