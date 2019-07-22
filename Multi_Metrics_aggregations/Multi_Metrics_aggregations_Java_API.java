private void setSource(SearchRequest searchRequest, List<SearchCriteria> searchCriteria, Collection<AggregationBuilder> aggregation) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        aggregation.forEach(a -> {
            if (searchSourceBuilder.aggregations() == null) {
                searchSourceBuilder.aggregation(a);
                return;
            }
            searchSourceBuilder.aggregations().addAggregator(a);
        });
    }
	
private Collection<AggregationBuilder> getStatisticalSummeryAggregation() {
        AggregatorFactories.Builder aggregations = new AggregatorFactories.Builder();
        ValueCountAggregationBuilder totalCount = AggregationBuilders.count("total_count").field("id.keyword");
        aggregations.addAggregator(totalCount);
        CardinalityAggregationBuilder callerDistinctCount = AggregationBuilders.cardinality("caller_distinct_count").field("caller.keyword");
        aggregations.addAggregator(callerDistinctCount);
        CardinalityAggregationBuilder userDistinctCount = AggregationBuilders.cardinality("user_distinct_count").field("user.keyword");
        aggregations.addAggregator(userDistinctCount);
        AvgAggregationBuilder durationAvg = AggregationBuilders.avg("times_avg").field("time");
        aggregations.addAggregator(durationAvg);
        TermsAggregationBuilder sentiments = AggregationBuilders.terms("languages_term").field("language.keyword");
        aggregations.addAggregator(sentiments);
        return aggregations.getAggregatorFactories();
    }