package org.finnex.mdiary.diary.header;

import org.finnex.mdiary.diary.entry.EntryField;

public interface Validator
{
	boolean check(EntryField<?> field);
}
