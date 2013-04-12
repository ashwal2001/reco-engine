package com.xyz.recommendation.demo;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class CountReader extends RecordReader<Text, Text> {
	private LineRecordReader lineReader;
	private Text lineKey;
	private Text lineValue;

	public void close() throws IOException {
		lineReader.close();
	}

	public Text getCurrentKey() throws IOException, InterruptedException {
		return lineKey;
	}

	public Text getCurrentValue() throws IOException, InterruptedException {
		return lineValue;
	}

	public float getProgress() throws IOException, InterruptedException {
		return lineReader.getProgress();
	}

	public void initialize(InputSplit is, TaskAttemptContext tac)
			throws IOException, InterruptedException {
		lineReader = new LineRecordReader();
		lineReader.initialize(is, tac);
	}

	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (!lineReader.nextKeyValue()) {
			return false;
		}

		String[] parts = lineReader.getCurrentValue().toString().split("\\s");

		lineValue = new Text(parts[1]);
		lineKey = new Text(parts[0]);

		return true;
	}
}
