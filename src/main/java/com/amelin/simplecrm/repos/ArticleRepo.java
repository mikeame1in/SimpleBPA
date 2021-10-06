package com.amelin.simplecrm.repos;

import com.amelin.simplecrm.domain.order.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepo extends CrudRepository<Article, Long> {
    Article findByOrder_Id(Integer order_id);
}
