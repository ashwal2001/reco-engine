package com.xyz.recommendation;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class JaccardMahoutDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		RandomUtils.useTestSeed();

		// specifying the user id to which the recommendations have to be
		// generated for
		int userId = 503;

		// specifying the number of recommendations to be generated
		int noOfRecommendations = 5;

		MongoDBDataModel dbm = new MongoDBDataModel("127.0.0.1", 27017,
				"products", "userLog", false, false, null);

		ItemSimilarity similarity = new LogLikelihoodSimilarity(dbm);

		/* Initalizing the recommender */
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(dbm,
				similarity);

		// calling the recommend method to generate recommendations
		List<RecommendedItem> recommendations = recommender.recommend(501, 3);

		//
		for (RecommendedItem recommendedItem : recommendations)
			System.out.println(recommendedItem.getItemID());
	}

}
