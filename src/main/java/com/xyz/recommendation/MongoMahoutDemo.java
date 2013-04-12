package com.xyz.recommendation;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class MongoMahoutDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		RandomUtils.useTestSeed();

		MongoDBDataModel dbm = new MongoDBDataModel("127.0.0.1", 27017, "mongo_hadoop",
				"ratings", false, false, null);
		SVDRecommender svd = new SVDRecommender(dbm, new ALSWRFactorizer(dbm,
				3, 0.05f, 50));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dbm);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
				similarity, dbm);
		Recommender recommender = new GenericUserBasedRecommender(dbm,
				neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(1, 1);
		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}

	}

}
