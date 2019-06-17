package me.stevenkin.beetle.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cookie {
    private String  name     = null;
    private String  value    = null;
    private String  domain   = null;
    private String  path     = "/";
    private long    maxAge   = -1;
    private boolean secure   = false;
    private boolean httpOnly = false;
}
