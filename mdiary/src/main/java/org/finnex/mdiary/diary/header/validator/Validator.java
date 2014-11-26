package org.finnex.mdiary.diary.header.validator;

public interface Validator
{
	<E> boolean validate(E value);
}
