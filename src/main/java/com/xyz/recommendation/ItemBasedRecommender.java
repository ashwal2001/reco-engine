package com.xyz.recommendation;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

public class ItemBasedRecommender {
	public static void main(String[] args) throws Exception {
		RandomUtils.useTestSeed();
		DataModel model = new FileDataModel(new File(
				"/home/ashok/data/dev/mahout/reco-engine/data/intro.csv"));

		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model)
					throws TasteException {
				ItemSimilarity similarity = new EuclideanDistanceSimilarity(
						model);
				return new GenericItemBasedRecommender(model, similarity);
			}
		};

		Recommender recommender = recommenderBuilder.buildRecommender(model);
		List<RecommendedItem> recomendations = recommender.recommend(1, 5);
		for (RecommendedItem recommendedItem : recomendations) {
			System.out.println(recommendedItem);
		}

		RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
		IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null,
				model, null, 2,
				GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);

		System.out.println(stats.getPrecision());
		System.out.println(stats.getRecall());
	}
}