package com.xyz.recommendation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommender {
	public static void main(String args[]) {
		// specifying the user id to which the recommendations have to be
		// generated for
		int userId = 510;

		// specifying the number of recommendations to be generated
		int noOfRecommendations = 5;

		try {
			// Data model created to accept the input file
			FileDataModel dataModel = new FileDataModel(new File(
					"D://input.txt"));

			/* Specifies the Similarity algorithm */
			ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(
					dataModel);

			/* Initalizing the recommender */
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(
					dataModel, itemSimilarity);

			// calling the recommend method to generate recommendations
			List<RecommendedItem> recommendations = recommender.recommend(
					userId, noOfRecommendations);

			//
			for (RecommendedItem recommendedItem : recommendations)
				System.out.println(recommendedItem.getItemID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
