package io.redspark.dbunitspring.sample;

import java.io.InputStream;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

/**
 * Substitui os valores da colunas com a String '[null]' por valores <code>null<code>.
 * 
 * @author vinicius.moreira
 */
public class CustomDatasetLoader extends AbstractDataSetLoader {

	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		InputStream inputStream = resource.getInputStream();
		try {
			FlatXmlDataSet dataset = builder.build(inputStream);
			ReplacementDataSet decoratedDataset = new ReplacementDataSet(dataset);
			decoratedDataset.addReplacementObject("[null]", null);
			return decoratedDataset;
			
		} finally {
			inputStream.close();
		}
	}
}
