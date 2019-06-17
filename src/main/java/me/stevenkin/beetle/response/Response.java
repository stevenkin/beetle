package me.stevenkin.beetle.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.stevenkin.beetle.request.Request;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Request request;
    private byte[] body;
}
