package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.pipeline.bucketselector.BucketSelectorPipelineAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestAggregation {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("10.65.3.17", 19200, "http")));



        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("start_time").gte(1585808916648L));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("start_time").lte(1585812516648L));
        // 根据字段count进行聚合，聚合后的数据放到桶by_count里
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("component_id").field("component_id");
        // 把桶里面的数据进行函数聚合
        TermsAggregationBuilder destAgg = AggregationBuilders.terms("dest_service_name").field("dest_service_name");
        aggregationBuilder.subAggregation(destAgg);

        //声明BucketPath，用于后面的bucket筛选
        Map<String, String> bucketsPathsMap = new HashMap<>(8);
        bucketsPathsMap.put("count", "_count");
        //设置脚本
        Script script = new Script("params.count >= 500");

        //构建bucket选择器
        BucketSelectorPipelineAggregationBuilder bs =
                PipelineAggregatorBuilders.bucketSelector("having", bucketsPathsMap, script);
        destAgg.subAggregation(bs);

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_service_instance_relation");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder.toString());
        SearchResponse searchResponse = client.search(searchRequest);
//        for (SearchHit searchHit : searchResponse.getHits()) {
//            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
//            System.out.println(sourceAsMap);
//        }
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms all = aggregations.get("component_id");
//        for (Terms.Bucket bucket : all.getBuckets()) {
//            ParsedSum sum_count = bucket.getAggregations().get("sum_count");
//            ParsedSum sum_value = bucket.getAggregations().get("sum_value");
//            System.out.println(bucket.getKey());
//            System.out.println(sum_count.getValue());
//            System.out.println(sum_value.getValue());
//        }



        client.close();
    }
}
