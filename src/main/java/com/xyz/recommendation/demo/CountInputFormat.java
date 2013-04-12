package com.xyz.recommendation.demo;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class CountInputFormat extends FileInputFormat<Text, Text> {
	public RecordReader<Text, Text> createRecordReader(InputSplit is,
			TaskAttemptContext tac) throws IOException, InterruptedException {
		CountReader cr = new CountReader();
		cr.initialize(is, tac);
		return cr;
	}
}
