package org.zero.spring.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class IndexOperateComponent {

	@Autowired
	private ElasticsearchTemplate template;

	public void create(String indexName) {
		template.createIndex(indexName);
	}
}
