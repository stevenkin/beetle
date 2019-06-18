package me.stevenkin.beetle.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private String title;
    private String resume;
    private String link;
    private String site;
    private String tags;
}
