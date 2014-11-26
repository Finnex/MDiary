package org.finnex.mdiary.diary.header;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

public class AbstractHeaderTest
{

	@Test
	public void testGetColumnByName()
	{
		DefaultHeader header = PowerMock.
				createPartialMockForAllMethodsExcept(DefaultHeader.class, "getColumnByName");
		
		List<Column> column = new ArrayList<Column>();
		Column testColumn = new Column("Test", null);
		column.add(testColumn);
		
		Whitebox.setInternalState(header, "columns", column);
		PowerMock.replay(header);
		
		Column result = header.getColumnByName("Test");
		
		assertThat(result, equalTo(testColumn));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetColumnByNameWithNull()
	{
		DefaultHeader header = PowerMock.
				createPartialMockForAllMethodsExcept(DefaultHeader.class, "getColumnByName");
		
		List<Column> column = new ArrayList<Column>();
		Column testColumn = new Column("Test", null);
		column.add(testColumn);
		
		Whitebox.setInternalState(header, "columns", column);
		PowerMock.replay(header);
		
		Column result = header.getColumnByName(null);
		
		assertThat(result, equalTo(testColumn));
	}

}
