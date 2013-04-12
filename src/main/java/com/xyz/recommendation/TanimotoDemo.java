package com.xyz.recommendation;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.BooleanUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Demonstrates TanimotoCoefficientSimilarity + recommender.
 * 
 */
public class TanimotoDemo {

	private DataModel dataModel;
	private ItemSimilarity tanimoto;

	private long CUSTOMER_A = 0;
	private long CUSTOMER_B = 1;
	private long CUSTOMER_C = 2;

	private long productOne = 0;
	private long productTwo = 1;
	private long productThree = 2;
	private long productFour = 3;
	private long productFive = 4;

	@Before
	public void setup() {
		FastByIDMap<PreferenceArray> userIdMap = new FastByIDMap<PreferenceArray>();

		BooleanUserPreferenceArray customerAPrefs = new BooleanUserPreferenceArray(
				4);
		customerAPrefs.set(0, new BooleanPreference(CUSTOMER_A, productOne));
		customerAPrefs.set(1, new BooleanPreference(CUSTOMER_A, productTwo));
		customerAPrefs.set(2, new BooleanPreference(CUSTOMER_A, productFour));
		customerAPrefs.set(3, new BooleanPreference(CUSTOMER_A, productFive));

		BooleanUserPreferenceArray customerBPrefs = new BooleanUserPreferenceArray(
				3);
		customerBPrefs.set(0, new BooleanPreference(CUSTOMER_B, productTwo));
		customerBPrefs.set(1, new BooleanPreference(CUSTOMER_B, productThree));
		customerBPrefs.set(2, new BooleanPreference(CUSTOMER_B, productFive));

		BooleanUserPreferenceArray customerCPrefs = new BooleanUserPreferenceArray(
				2);
		customerCPrefs.set(0, new BooleanPreference(CUSTOMER_C, productOne));
		customerCPrefs.set(1, new BooleanPreference(CUSTOMER_C, productFive));

		userIdMap.put(CUSTOMER_A, customerAPrefs);
		userIdMap.put(CUSTOMER_B, customerBPrefs);
		userIdMap.put(CUSTOMER_C, customerCPrefs);

		dataModel = new GenericDataModel(userIdMap);
		tanimoto = new TanimotoCoefficientSimilarity(dataModel);
	}

	@Test
	public void testSimilarities() throws TasteException {
		assertEquals((double) 1,
				tanimoto.itemSimilarity(productOne, productOne), 0.01);
		assertEquals((double) 1 / 3,
				tanimoto.itemSimilarity(productOne, productTwo), 0.01);
		assertEquals((double) 0,
				tanimoto.itemSimilarity(productOne, productThree), 0.01);
		assertEquals((double) 1 / 2,
				tanimoto.itemSimilarity(productOne, productFour), 0.01);
		assertEquals((double) 2 / 3,
				tanimoto.itemSimilarity(productOne, productFive), 0.01);

		assertEquals((double) 1 / 1,
				tanimoto.itemSimilarity(productTwo, productTwo), 0.01);
		assertEquals((double) 1 / 2,
				tanimoto.itemSimilarity(productTwo, productThree), 0.01);
		assertEquals((double) 1 / 2,
				tanimoto.itemSimilarity(productTwo, productFour), 0.01);
		assertEquals((double) 2 / 3,
				tanimoto.itemSimilarity(productTwo, productFive), 0.01);

		assertEquals((double) 1,
				tanimoto.itemSimilarity(productThree, productThree), 0.01);
		assertEquals((double) 0,
				tanimoto.itemSimilarity(productThree, productFour), 0.01);
		assertEquals((double) 1 / 3,
				tanimoto.itemSimilarity(productThree, productFive), 0.01);

		assertEquals((double) 1,
				tanimoto.itemSimilarity(productFour, productFour), 0.01);
		assertEquals((double) 1 / 3,
				tanimoto.itemSimilarity(productFour, productFive), 0.01);

		assertEquals((double) 1,
				tanimoto.itemSimilarity(productFive, productFive), 0.01);
	}

	@Test
	public void testRecommendProducts() throws TasteException {
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(
				dataModel, tanimoto);

		List<RecommendedItem> similarToProductThree = recommender
				.mostSimilarItems(productThree, 2);

		assertEquals(productTwo, similarToProductThree.get(0).getItemID());
		assertEquals(productFive, similarToProductThree.get(1).getItemID());
	}
}