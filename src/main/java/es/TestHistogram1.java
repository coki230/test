package es;

import cn.hutool.core.date.DateUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class TestHistogram1 {
    public static void main(String[] args) throws IOException {
        TestHistogram1 testHistogram1 = new TestHistogram1();

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.65.3.17", 19200, "http")));

        testHistogram1.getApdexGraph(client);

        client.close();
    }

    private void getApdexGraph(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("time_bucket").field("time_bucket");
        aggregationBuilder.subAggregation(AggregationBuilders.sum("total").field("total"));
        aggregationBuilder.subAggregation(AggregationBuilders.range("latency").addRange("latency", 0, 200));
        aggregationBuilder.subAggregation(AggregationBuilders.sum("tolerating").field("tolerating"));
        aggregationBuilder.size(10000);
        aggregationBuilder.order(BucketOrder.key(true));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("time_bucket").gte("202006120800").lte("202006121800"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.size(0);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_segment");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
    }

    private void getGraph(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("time_bucket").field("time_bucket");
        aggregationBuilder.subAggregation(AggregationBuilders.sum("total").field("total"));
        aggregationBuilder.subAggregation(AggregationBuilders.sum("satisfied").field("satisfied"));
        aggregationBuilder.subAggregation(AggregationBuilders.sum("tolerating").field("tolerating"));
        DateHistogramInterval interval = DateHistogramInterval.MINUTE;
        aggregationBuilder.dateHistogramInterval(interval);
        aggregationBuilder.minDocCount(0);
        ExtendedBounds extendedBounds = new ExtendedBounds("1591920000000", "1591956000000");
        aggregationBuilder.extendedBounds(extendedBounds);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("service_id", 23));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("latency").gte(200));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("start_time").gte("1591920000000").lte("1591956000000"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.size(0);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_segment");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
    }

    private void getTop5(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("endpoint_name").field("endpoint_name");
        aggregationBuilder.subAggregation(AggregationBuilders.avg("latency").field("latency"));
        aggregationBuilder.order(BucketOrder.count(false));
        aggregationBuilder.size(10000);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("service_id", 23));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("start_time").gte("1591920000000").lte("1591956000000"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.size(0);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_span_info");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
    }

    private void getNum(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("pid", "dops"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("begin").gte("1591920000000").lte("1591956000000"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(0);
        SearchRequest searchRequest = new SearchRequest("cloud-monitor_span_info");
        searchRequest.types("type");
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
    }
}
