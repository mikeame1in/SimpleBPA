package com.amelin.simplecrm.domain.order;

import javax.persistence.*;
import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String note;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private List<Article> article;
}
